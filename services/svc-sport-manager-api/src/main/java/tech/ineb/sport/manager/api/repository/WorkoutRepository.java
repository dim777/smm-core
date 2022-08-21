package tech.ineb.sport.manager.api.repository;

import tech.ineb.sport.lib.common.models.dto.WorkoutDTO;

import java.util.Optional;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
public interface WorkoutRepository<ID> {
  Optional<WorkoutDTO> findById(ID id);
}
