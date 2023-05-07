package tech.ineb.sport.manager.api.repository;

import org.springframework.data.domain.Page;
import tech.ineb.sport.lib.common.models.dto.AthleteDTO;

import java.util.UUID;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
public interface FollowersRepository {
  Page<AthleteDTO> findAllForAthleteId(UUID id);
}
