package tech.ineb.sport.manager.api.services;

import tech.ineb.sport.lib.common.models.dto.AthleteDTO;

import java.util.UUID;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
public interface AthletesService {
  AthleteDTO findById(UUID athleteId);
}
