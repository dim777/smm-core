package tech.ineb.sport.manager.api.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tech.ineb.sport.lib.common.models.dto.CredentialsDTO;
import tech.ineb.sport.lib.common.models.dto.CredentialsDTO.CredentialsDTOBuilder;
import tech.ineb.sport.lib.common.models.rest.AuthReq;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-21T13:18:16+0300",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 15.0.1 (AdoptOpenJDK)"
)
@Component
public class AuthMapperImpl implements AuthMapper {

    @Override
    public CredentialsDTO toDTO(AuthReq authReq) {
        if ( authReq == null ) {
            return null;
        }

        CredentialsDTOBuilder credentialsDTO = CredentialsDTO.builder();

        credentialsDTO.athleteId( authReq.getAthleteId() );
        credentialsDTO.username( authReq.getUsername() );
        credentialsDTO.password( authReq.getPassword() );

        return credentialsDTO.build();
    }
}
