package tech.ineb.sport.manager.api.integration.strava;

import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;
import tech.ineb.sport.lib.common.models.dto.AthleteDTO;
import tech.ineb.sport.lib.common.models.dto.AthletePlatformDTO;
import tech.ineb.sport.lib.common.models.dto.CredentialsDTO;
import tech.ineb.sport.lib.common.models.dto.PlatformDTO;
import tech.ineb.sport.lib.common.models.tables.pojos.Athlete;
import tech.ineb.sport.lib.common.models.tables.pojos.AthleteFollowers;
import tech.ineb.sport.lib.common.strava.model.DetailedAthlete;
import tech.ineb.sport.manager.api.ex.AthleteNotFoundEx;
import tech.ineb.sport.manager.api.ex.PlatformNotFoundEx;
import tech.ineb.sport.manager.api.ex.SyncWithPlatformEx;
import tech.ineb.sport.manager.api.integration.IntegrationAdapter;
import tech.ineb.sport.manager.api.integration.Session;
import tech.ineb.sport.manager.api.mappers.AthleteMapper;
import tech.ineb.sport.manager.api.repository.AthleteRepository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class maintains ...
 * todo: webdriver should be defined as interface (without specific realization)
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
@AllArgsConstructor @Component @Slf4j
public class StravaIntegrationAdapter implements IntegrationAdapter<WebDriver> {
  //todo: bad idea
  private static final String STRAVA_PLATFORM_CODE = "STRV0";

  private final StravaIntegration stravaIntegration;
  private final AthleteRepository athleteRepository;
  private final AthleteMapper athleteMapper;

  @Override public String code() {
    return STRAVA_PLATFORM_CODE;
  }

  @Override public Session<WebDriver> auth(CredentialsDTO credentials) {
    return stravaIntegration.auth(credentials);
  }

  @Override public CompletableFuture<List<AthleteDTO>> syncFollowers(AthleteDTO athlete) {
    final Long stravaId = getStravaId(athlete);
    final UUID athleteId = athlete.getId();
    log.info("Find athlete by id = '{}'", athleteId);

    final AthleteDTO savedAthleteDTO = athleteRepository
        .findByIdAndPlatformCode(athleteId, STRAVA_PLATFORM_CODE)
        //todo: or else save new account
        .orElseThrow(() ->
            new SyncWithPlatformEx("Athlete should be defined prior", STRAVA_PLATFORM_CODE));

    final PlatformDTO platform = savedAthleteDTO.getPlatforms().stream()
        .filter(p -> p.getPlatform().getCode().equals(STRAVA_PLATFORM_CODE))
        .findAny()
        .map(new Function<AthletePlatformDTO, PlatformDTO>() {
          @Override public PlatformDTO apply(AthletePlatformDTO athletePlatformDTO) {
            return athletePlatformDTO.getPlatform();
          }
        })
        .orElseThrow(() ->
            new SyncWithPlatformEx("Athlete platform should be defined first", STRAVA_PLATFORM_CODE));

    final List<DetailedAthlete> followersByAthleteID = stravaIntegration.findFollowersByAthleteID(stravaId);

    //todo: batch update should be done
    final List<AthleteFollowers> athleteFollowers = followersByAthleteID.stream()
        .map(detailedAthlete -> {
          final Athlete a = athleteRepository
              .findAthleteForPlatformSpecificId(detailedAthlete.getId(), STRAVA_PLATFORM_CODE)
              .orElseGet(() -> saveAthlete(detailedAthlete, platform));
          log.debug("Processing follower athlete='{}'", a);
          return a;
        })
        .map(a -> athleteRepository.getOrCreateFollowerRelation(savedAthleteDTO.getId(), a.getId()))
        .collect(Collectors.toList());

    //todo: refactor to batch select
    final List<AthleteDTO> athletes = athleteFollowers.stream()
        .map(AthleteFollowers::getAthleteFollowerId)
        .map(athleteId1 -> athleteRepository.findById(athleteId1)
            .orElse(null))
        .collect(Collectors.toList());
    return CompletableFuture.completedFuture(athletes);
  }

  @Override public CompletableFuture<AthleteDTO> syncFollowing(AthleteDTO athlete) {
    final Long stravaId = getStravaId(athlete);
    final UUID athleteId = athlete.getId();
    log.info("Find athlete by id = '{}'", athleteId);

    final AthleteDTO savedAthleteDTO = athleteRepository
        .findByIdAndPlatformCode(athleteId, STRAVA_PLATFORM_CODE)
        //todo: or else save new account
        .orElseThrow(() -> new SyncWithPlatformEx("Athlete should be defined prior", "Strava"));

    final PlatformDTO platform = savedAthleteDTO.getPlatforms().stream()
        .filter(p -> p.getPlatform().getCode().equals(STRAVA_PLATFORM_CODE))
        .findAny()
        .map(new Function<AthletePlatformDTO, PlatformDTO>() {
          @Override public PlatformDTO apply(AthletePlatformDTO athletePlatformDTO) {
            return athletePlatformDTO.getPlatform();
          }
        })
        .orElseThrow(() ->
            new SyncWithPlatformEx("Athlete platform should be defined first", STRAVA_PLATFORM_CODE));

    final List<DetailedAthlete> followingByAthleteID = stravaIntegration.findFollowingByAthleteID(stravaId);

    followingByAthleteID.stream()
        .map(detailedAthlete -> {
          final Athlete a = athleteRepository
              .findAthleteForPlatformSpecificId(detailedAthlete.getId(), STRAVA_PLATFORM_CODE)
              .orElseGet(() -> saveAthlete(detailedAthlete, platform));
          log.debug("Processing following athlete='{}'", a);
          return a;
        })
        .forEach(a -> {
          final UUID parentId = athlete.getId();
          final UUID childId = a.getId();
          if (athleteRepository
              .find(parentId, childId)
              .isEmpty()) {
            final AthleteDTO athleteDTO = athleteRepository
                .findByIdAndPlatformCode(childId, STRAVA_PLATFORM_CODE)
                .orElseThrow(() -> new AthleteNotFoundEx("Couldn't find athlete"));
            final Long childStravaId = getStravaId(athleteDTO);
            final Boolean cancelSubscription = stravaIntegration.cancelSubscription(childStravaId);
            log.debug("Cancel subscription is = '{}'", cancelSubscription);
          }
        });
    return null;
  }

  private Long getStravaId(AthleteDTO athleteDTO) {
    String ID = "id";
    final AthletePlatformDTO athletePlatform = athleteDTO.getPlatforms().stream()
        .filter(p -> p.getPlatform().getCode().equals(STRAVA_PLATFORM_CODE))
        .findAny()
        .orElseThrow(() -> new PlatformNotFoundEx("Couldn't find Strava platform"));
    final JsonElement athletePlatformMeta = athletePlatform.getAthletePlatformMeta();
    return athletePlatformMeta.getAsJsonObject().entrySet()
        .stream()
        .filter(el -> el.getKey().equals(ID))
        .map(el -> el.getValue().getAsLong())
        .findAny()
        .orElseThrow(() -> new SyncWithPlatformEx("Strava", "Couldn't get Athlete strava id"));
  }

  private Athlete saveAthlete(DetailedAthlete detailedAthlete, PlatformDTO platformDTO) {
    final AthleteDTO a = athleteMapper.toDTO(detailedAthlete, platformDTO);
    log.debug("Mapped detailedAthlete to athlete='{}'", a);
    return athleteRepository.save(a);
  }
}
