package tech.ineb.sport.manager.api.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tech.ineb.sport.lib.common.models.dto.PlatformDTO;
import tech.ineb.sport.lib.common.models.tables.pojos.Platform;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-21T13:18:16+0300",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 15.0.1 (AdoptOpenJDK)"
)
@Component
public class PlatformMapperImpl implements PlatformMapper {

    @Override
    public PlatformDTO toDTO(Platform platform) {
        if ( platform == null ) {
            return null;
        }

        PlatformDTO platformDTO = new PlatformDTO();

        platformDTO.setId( platform.getId() );
        platformDTO.setCode( platform.getCode() );
        platformDTO.setName( platform.getName() );

        return platformDTO;
    }
}
