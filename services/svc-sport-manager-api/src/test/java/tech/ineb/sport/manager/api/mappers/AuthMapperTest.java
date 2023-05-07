package tech.ineb.sport.manager.api.mappers;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.util.Assert;
import tech.ineb.sport.lib.common.models.dto.CredentialsDTO;
import tech.ineb.sport.lib.common.models.dto.PlatformDTO;
import tech.ineb.sport.lib.common.models.rest.AuthReq;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
public class AuthMapperTest {
    @Mock
    AuthMapper mapper;

    @Test
    @Disabled
    public void givenNullCorrelationId_thenDefaultIdShouldBeSet() {
        AuthReq mockAuthReq = new AuthReq();
        final CredentialsDTO credentialsDTO = mapper.toDTO(mockAuthReq);
        Assert.notNull(credentialsDTO, "credentialsDTO should be not null");
    }
}