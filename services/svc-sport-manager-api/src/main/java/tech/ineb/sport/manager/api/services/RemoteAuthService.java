//package tech.ineb.sport.manager.api.services;
//
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import tech.ineb.sport.lib.common.models.dto.AthleteDTO;
//import tech.ineb.sport.lib.common.models.dto.CredentialsDTO;
//import tech.ineb.sport.lib.common.models.dto.PlatformDTO;
//import tech.ineb.sport.manager.api.ex.AuthWithPlatformEx;
//import tech.ineb.sport.manager.api.integration.IntegrationAdapter;
//import tech.ineb.sport.manager.api.mappers.AuthMapper;
//
//import java.util.Map;
//
///**
// * This class maintains ...
// *
// * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
// */
//@AllArgsConstructor @Service @Slf4j
//public class RemoteAuthService implements AuthService {
//  //integration adapters registry
//  private final Map<String, IntegrationAdapter> adapters;
//  private final PlatformService platformService;
//  private final AthletesService athletesService;
//  private final AuthMapper authMapper;
//
//  @Override public AthleteDTO auth(AuthRequestDTO authRequest) {
//    final PlatformDTO platform = platformService.findByCode(authRequest.getPlatformCode());
//    CredentialsDTO credentialsDTO = authMapper.toDTO(authRequest, platform);
//    final AthleteDTO athleteDTO = athletesService.findById(credentialsDTO.getAthleteId());
//    log.debug("Find following Athlete = '{}'", athleteDTO);
//
//    if (adapters.containsKey(platformCode)) {
//      final IntegrationAdapter integrationAdapter = adapters.get(platformCode);
//      integrationAdapter.auth(credentialsDTO);
//      log.debug("Successfully make auth request");
//      return athleteDTO;
//    }
//    throw new AuthWithPlatformEx(credentialsDTO.getPlatformDTO().getName(),
//        "Couldn't process athlete with id = '" + credentialsDTO.getAthleteId() + "'");
//  }
//}
