//package tech.ineb.sport.manager.api.services;
//
//import com.google.gson.JsonElement;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Lookup;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import tech.ineb.sport.lib.common.models.dto.AthleteDTO;
//import tech.ineb.sport.lib.common.models.dto.AthleteWithCredentialsDTO;
//import tech.ineb.sport.lib.common.models.dto.PlatformDTO;
//import tech.ineb.sport.lib.common.models.tables.pojos.Athlete;
//import tech.ineb.sport.lib.common.models.tables.pojos.AthleteFollowers;
//import tech.ineb.sport.lib.common.strava.model.DetailedAthlete;
//import tech.ineb.sport.manager.api.ex.SyncWithPlatformEx;
//import tech.ineb.sport.manager.api.integration.strava.StravaIntegration;
//import tech.ineb.sport.manager.api.mappers.AthleteMapper;
//import tech.ineb.sport.manager.api.repository.AthleteRepository;
//
//import java.util.List;
//import java.util.UUID;
//import java.util.concurrent.CompletableFuture;
//import java.util.stream.Collectors;
//
///**
// * This class maintains ...
// *
// * @author Dmitriy Erokhin dim777@ya.ru
// */
//@Service @AllArgsConstructor @Slf4j
//public class StravaSyncService implements SyncService {
//  public static final int DEFAULT_STRAVA_SIZE = 25;
//
//
//
//  @Override @Async public CompletableFuture<AthleteDTO> sync(AthleteWithCredentialsDTO athleteWithCredentialsDTO) {
//    final UUID athleteId = athleteWithCredentialsDTO.getId();
//    log.info("Find athlete by id = '{}'", athleteId);
//
//    final AthleteDTO savedAthleteDTO = athleteRepository
//        .findByIdAndPlatformCode(athleteId, STRAVA_PLATFORM_CODE)
//        //todo: or else save new account
//        .orElseThrow(() -> new SyncWithPlatformEx("Athlete should be defined prior", "Strava"));
//
//
//    return CompletableFuture.completedFuture(savedAthleteDTO);
//  }
//
//}
