package tech.ineb.sport.manager.api.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ineb.sport.lib.common.models.dto.WorkoutDTO;

import java.net.URI;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
@RestController @RequestMapping(("/api/v1/workout"))
@AllArgsConstructor @Slf4j
public class WorkoutController {
//  private final WorkoutRepository<UUID> workoutRepository;

  /**
   * getActivityById
   *
   * @return responseEntity
   */
  public ResponseEntity<WorkoutDTO> getLatestWorkout(@RequestBody String id) {
    final UUID uuid = UUID.fromString(id);
//    workoutRepository.findById()
//    return
    return null;
  }

  /**
   * Get Response Entity
   *
   * @param uri uri
   * @return ResponseEntity<WorkoutDTO>
   */
  private Callable<ResponseEntity<WorkoutDTO>> getResponseEntity(URI uri) {
//    return () -> stravaRestTemplate.exchange(
//        uri,
//        HttpMethod.GET,
//        null,
//        WorkoutDTO.class);
    return null;
  }
}
