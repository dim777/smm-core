package tech.ineb.sport.manager.api.mappers;

import org.junit.Test;
import org.springframework.util.Assert;
import tech.ineb.sport.lib.common.models.dto.CredentialsDTO;
import tech.ineb.sport.lib.common.models.dto.PlatformDTO;
import tech.ineb.sport.lib.common.models.rest.AuthReq;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
public class AuthMapperTest {
  private final AuthMapper mapper = new AuthMappperImpl();

  @Test
  public void givenNullCorrelationId_thenDefaultIdShouldBeSet() {
    AuthReq mockAuthReq = new AuthReq();
    PlatformDTO mockPlatformDTO = new PlatformDTO();
    final CredentialsDTO credentialsDTO = mapper.toDTO(mockAuthReq, mockPlatformDTO);
    Assert.notNull(credentialsDTO, "credentialsDTO should be not null");
  }
}