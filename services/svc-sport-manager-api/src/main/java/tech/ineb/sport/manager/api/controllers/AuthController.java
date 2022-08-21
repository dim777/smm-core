package tech.ineb.sport.manager.api.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ineb.sport.lib.common.models.dto.CredentialsDTO;
import tech.ineb.sport.lib.common.models.rest.AuthReq;
import tech.ineb.sport.lib.common.models.rest.CommonReq;
import tech.ineb.sport.manager.api.integration.IntegrationAdapter;
import tech.ineb.sport.manager.api.integration.IntegrationAdaptersRegistry;
import tech.ineb.sport.manager.api.integration.Session;
import tech.ineb.sport.manager.api.mappers.AuthMapper;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
@RestController @RequestMapping(("/api/v1/auth"))
@AllArgsConstructor @Slf4j
public class AuthController {
  private final IntegrationAdaptersRegistry registry;
  private final AuthMapper mapper;

  @RequestMapping(value = "/{platformCode}", method = RequestMethod.POST, produces = "application/json")
  @ApiOperation(value = "Make auth request to Platform", response = ResponseEntity.class)
  public ResponseEntity<Void> authWithPlatform(@PathVariable String platformCode, @RequestBody CommonReq<AuthReq> authReq) {
    log.debug("Get following auth request to platform = '{}', with following authReq = '{}'", platformCode, authReq);

    final CredentialsDTO credentialsDTO = mapper.toDTO(authReq.getPayload());
    log.debug("Mapped to CredentialsDTO = '{}'", credentialsDTO);

    final IntegrationAdapter<?> adapter = registry.findByCode(platformCode);

    final Session<?> session = adapter.auth(credentialsDTO);
    log.debug("Session = '{}'", session);

    return ResponseEntity.noContent().build();
  }
}
