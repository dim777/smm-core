package tech.ineb.sport.manager.api.repository;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tech.ineb.sport.lib.common.models.dto.WorkoutDTO;
import tech.ineb.sport.lib.common.strava.model.DetailedActivity;
import tech.ineb.sport.manager.api.config.AppConfig;
import tech.ineb.sport.manager.api.mappers.ActivityMapper;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
@Repository
@AllArgsConstructor
@Slf4j
public class ApiBasedActivityRepository implements WorkoutRepository<UUID> {
  private final RestTemplate stravaRestTemplate;
  private final AppConfig.StravaConfig stravaConfig;
  private final ActivityMapper mapper;
  private final ExecutorService executor;

  /**
   * getActivityById /activities/{id}
   *
   * @param id id
   * @return
   */
  @Override
  public Optional<WorkoutDTO> findById(@NotNull UUID id) {
    final URI uri = UriComponentsBuilder
        .fromHttpUrl(stravaConfig.getUrl())
        .queryParam("id", id)
        .encode().build().toUri();

    try {
      final ResponseEntity<DetailedActivity> responseEntity = executor
          .submit(getResponseEntity(uri))
          .get(stravaConfig.getTimeout(), TimeUnit.SECONDS);

      if (responseEntity.getStatusCode().is2xxSuccessful()) {
        final DetailedActivity detailedActivity = responseEntity.getBody();
        log.info("Get following detailed activity = {}", detailedActivity);
        final WorkoutDTO workoutDTO = mapper.toDTO(detailedActivity);
        return Optional.of(workoutDTO);
      }
    } catch (TimeoutException e) {
      log.error("Error get customer by inn={} from registry. Reason:", "inn", e);
      throw new RuntimeException(String.format("Error get customer from registry. Reason: timeout %ss exceeded", "registry422Config.getTimeout()"));
    } catch (Exception e) {
      log.error("Error get customer by inn={} from registry. Reason:", "inn", e);
      throw new RuntimeException(String.format("Error get customer from registry. Reason: %s", e.getMessage()));
    }
    return Optional.empty();
  }

  /**
   * Get Response Entity
   *
   * @param uri uri
   * @return ResponseEntity<WorkoutDTO>
   */
  private Callable<ResponseEntity<DetailedActivity>> getResponseEntity(URI uri) {
    return () -> stravaRestTemplate.exchange(
        uri,
        HttpMethod.GET,
        null,
        DetailedActivity.class);
  }
}
