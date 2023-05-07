package tech.ineb.sport.manager.api.integration.zwift;

import org.springframework.stereotype.Component;
import tech.ineb.sport.lib.common.models.dto.AthleteDTO;
import tech.ineb.sport.lib.common.models.dto.CredentialsDTO;
import tech.ineb.sport.manager.api.integration.IntegrationAdapter;
import tech.ineb.sport.manager.api.integration.Session;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
@Component
public class ZwiftIntegrationAdapter implements IntegrationAdapter {
  @Override public String code() {
    return "ZWFT0";
  }

  @Override public Session<?> auth(CredentialsDTO credentials) {
    return null;
  }

  @Override public CompletableFuture<List<AthleteDTO>> syncFollowers(AthleteDTO athlete) {
    return null;
  }

  @Override public CompletableFuture<AthleteDTO> syncFollowing(AthleteDTO athlete) {
    return null;
  }
}
