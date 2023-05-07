package tech.ineb.sport.manager.api.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.ineb.sport.lib.common.models.dto.AthleteDTO;
import tech.ineb.sport.manager.api.ex.AthleteNotFoundEx;
import tech.ineb.sport.manager.api.repository.AthleteRepository;

import java.util.UUID;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
@AllArgsConstructor @Service @Slf4j
public class BaseAthleteService implements AthletesService {
  private final AthleteRepository athleteRepository;

  @Override @Transactional(readOnly = true)
  public AthleteDTO findById(UUID athleteId) {
    return athleteRepository
        .findById(athleteId)
        .orElseThrow(() -> new AthleteNotFoundEx("Couldn't find athlete for id = '" + athleteId + "'"));
  }
}
