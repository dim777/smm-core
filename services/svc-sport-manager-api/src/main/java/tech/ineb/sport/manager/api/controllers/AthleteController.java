package tech.ineb.sport.manager.api.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.ineb.sport.lib.common.models.dto.AthleteDTO;
import tech.ineb.sport.lib.common.models.rest.AthleteReq;
import tech.ineb.sport.lib.common.models.rest.CommonReq;
import tech.ineb.sport.manager.api.integration.IntegrationAdapter;
import tech.ineb.sport.manager.api.integration.IntegrationAdaptersRegistry;
import tech.ineb.sport.manager.api.services.AthletesService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
@RestController @RequestMapping(("/api/v1/athletes"))
@AllArgsConstructor @Slf4j
public class AthleteController {
  private final AthletesService athletesService;
  private final IntegrationAdaptersRegistry registry;

  /**
   * Sync athlete followers
   *
   * @param athleteRequest athlete id
   * @return ResponseEntity
   */
  @RequestMapping(value = "/sync/followers/{platformCode}", method = RequestMethod.POST, produces = "application/json")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Void> syncFollowers(@PathVariable String platformCode, @RequestBody @NonNull CommonReq<AthleteReq> athleteRequest) {
    final UUID correlationId = athleteRequest.getCorrelationId();
    log.info("Sync followers with correlation ID = '{}'", correlationId);

    final AthleteReq payload = athleteRequest.getPayload();
    final AthleteDTO athleteDTO = athletesService.findById(payload.getAthleteId());
    final IntegrationAdapter<?> adapter = registry.findByCode(platformCode);
    final CompletableFuture<List<AthleteDTO>> future = adapter.syncFollowers(athleteDTO);
    CompletableFuture.allOf(future).join();

    return ResponseEntity.noContent().build();
  }

  /**
   * Sync athlete followings
   *
   * @param athleteRequest athlete id
   * @return ResponseEntity
   */
  @RequestMapping(value = "/sync/followings/{platformCode}", method = RequestMethod.POST, produces = "application/json")
  public ResponseEntity<Void> syncFollowings(@PathVariable String platformCode, @RequestBody @NonNull CommonReq<AthleteReq> athleteRequest) {
    final UUID correlationId = athleteRequest.getCorrelationId();
    log.info("Sync followings with correlation ID = '{}'", correlationId);

    final AthleteReq payload = athleteRequest.getPayload();
    final AthleteDTO athleteDTO = athletesService.findById(payload.getAthleteId());
    final IntegrationAdapter<?> adapter = registry.findByCode(platformCode);
    final CompletableFuture<AthleteDTO> future = adapter.syncFollowing(athleteDTO);
    CompletableFuture.allOf(future).join();

    return ResponseEntity.noContent().build();
  }
}
