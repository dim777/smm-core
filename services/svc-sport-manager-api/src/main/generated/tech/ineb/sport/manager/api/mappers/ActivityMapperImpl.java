package tech.ineb.sport.manager.api.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tech.ineb.sport.lib.common.models.dto.WorkoutDTO;
import tech.ineb.sport.lib.common.strava.model.DetailedActivity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-21T13:18:16+0300",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 15.0.1 (AdoptOpenJDK)"
)
@Component
public class ActivityMapperImpl implements ActivityMapper {

    @Override
    public WorkoutDTO toDTO(DetailedActivity activity) {
        if ( activity == null ) {
            return null;
        }

        WorkoutDTO workoutDTO = new WorkoutDTO();

        return workoutDTO;
    }
}
