package tech.ineb.sport.manager.api.services;

import tech.ineb.sport.lib.common.models.dto.AthleteDTO;
import tech.ineb.sport.lib.common.models.dto.AthleteWithCredentialsDTO;
import tech.ineb.sport.manager.api.integration.strava.StravaIntegration;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
public interface SyncService {
  CompletableFuture<AthleteDTO> sync(AthleteWithCredentialsDTO athleteDTO);

  /**
   * Get, save and remove non-actual relations between athlete and follower
   *
   * @param athlete           athleteDTO
   * @param stravaIntegration Strava Integration component
   * @return List of actual followers
   */
  CompletableFuture<List<AthleteDTO>> syncFollowers(AthleteDTO athlete, StravaIntegration stravaIntegration);

  CompletableFuture<AthleteDTO> syncFollowing(AthleteDTO athlete);
}
