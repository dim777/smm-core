package tech.ineb.sport.manager.api.mappers;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tech.ineb.sport.lib.common.models.dto.WorkoutDTO;
import tech.ineb.sport.lib.common.strava.model.DetailedActivity;

import java.util.Objects;
import java.util.UUID;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
@Mapper(componentModel = "spring")
public interface ActivityMapper {
  @Mapping(target = "id", ignore = true)
  WorkoutDTO toDTO(DetailedActivity activity);

  @AfterMapping
  default void setDefaultID(WorkoutDTO workoutDTO) {
    if (Objects.isNull(workoutDTO.getId())) {
      workoutDTO.setId(UUID.randomUUID());
    }
  }
}
