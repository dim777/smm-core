package tech.ineb.sport.manager.api.integration;

import tech.ineb.sport.lib.common.models.dto.AthleteDTO;
import tech.ineb.sport.lib.common.models.dto.CredentialsDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
public interface IntegrationAdapter<T> {
  String code();

  Session<T> auth(CredentialsDTO credentials);

  /**
   * Get, save and remove non-actual relations between athlete and follower
   *
   * @param athlete athleteDTO
   * @return List of actual followers
   */
  CompletableFuture<List<AthleteDTO>> syncFollowers(AthleteDTO athlete);

  CompletableFuture<AthleteDTO> syncFollowing(AthleteDTO athlete);
}
