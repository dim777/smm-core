package tech.ineb.sport.manager.api.services;

import tech.ineb.sport.lib.common.models.dto.AthleteDTO;

import java.util.UUID;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
public interface AthletesService {
  AthleteDTO findById(UUID athleteId);
}
