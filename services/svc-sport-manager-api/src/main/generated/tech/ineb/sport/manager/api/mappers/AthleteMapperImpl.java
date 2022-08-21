package tech.ineb.sport.manager.api.mappers;

import com.google.gson.JsonElement;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.jooq.Record10;
import org.jooq.Record4;
import org.jooq.Record6;
import org.springframework.stereotype.Component;
import tech.ineb.sport.lib.common.models.dto.AthleteDTO;
import tech.ineb.sport.lib.common.models.dto.AthleteDTO.AthleteDTOBuilder;
import tech.ineb.sport.lib.common.models.dto.AthletePlatformDTO;
import tech.ineb.sport.lib.common.models.dto.PlatformDTO;
import tech.ineb.sport.lib.common.models.tables.pojos.Athlete;
import tech.ineb.sport.lib.common.strava.model.DetailedAthlete;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-21T13:18:16+0300",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 15.0.1 (AdoptOpenJDK)"
)
@Component
public class AthleteMapperImpl implements AthleteMapper {

    @Override
    public AthleteDTO toDTO(Record6<UUID, String, String, String, Integer, Integer> record) {
        if ( record == null ) {
            return null;
        }

        AthleteDTOBuilder athleteDTO = AthleteDTO.builder();

        athleteDTO.id( record.get(0, UUID.class) );
        athleteDTO.lastName( record.get(2, String.class) );
        athleteDTO.country( record.get(3, String.class) );
        athleteDTO.ftp( record.get(4, Integer.class) );

        return athleteDTO.build();
    }

    @Override
    public AthletePlatformDTO toDTO(Record4<JsonElement, UUID, String, String> record) {
        if ( record == null ) {
            return null;
        }

        AthletePlatformDTO athletePlatformDTO = new AthletePlatformDTO();

        athletePlatformDTO.setPlatform( jsonElementUUIDStringStringRecord4ToPlatformDTO( record ) );

        athletePlatformDTO.setAthletePlatformMeta( record.get(0, JsonElement.class) );

        return athletePlatformDTO;
    }

    @Override
    public AthleteDTO toDTO(Record10<UUID, String, String, String, Integer, JsonElement, UUID, String, String, Integer> record) {
        if ( record == null ) {
            return null;
        }

        AthleteDTOBuilder athleteDTO = AthleteDTO.builder();

        athleteDTO.id( record.get(0, UUID.class) );
        athleteDTO.firstName( record.get(1, String.class) );
        athleteDTO.lastName( record.get(2, String.class) );
        athleteDTO.country( record.get(3, String.class) );
        athleteDTO.ftp( record.get(4, Integer.class) );

        return athleteDTO.build();
    }

    @Override
    public AthleteDTO toDTO(DetailedAthlete detailedAthlete, PlatformDTO platform) {
        if ( detailedAthlete == null && platform == null ) {
            return null;
        }

        AthleteDTOBuilder athleteDTO = AthleteDTO.builder();

        if ( detailedAthlete != null ) {
            athleteDTO.lastName( detailedAthlete.getLastname() );
            athleteDTO.firstName( detailedAthlete.getFirstname() );
            athleteDTO.country( detailedAthlete.getCountry() );
            athleteDTO.ftp( detailedAthlete.getFtp() );
        }

        defaultId( athleteDTO );

        return athleteDTO.build();
    }

    @Override
    public AthleteDTO toDTO(Athlete athlete, PlatformDTO platform) {
        if ( athlete == null && platform == null ) {
            return null;
        }

        AthleteDTOBuilder athleteDTO = AthleteDTO.builder();

        if ( athlete != null ) {
            athleteDTO.id( athlete.getId() );
            athleteDTO.firstName( athlete.getFirstName() );
            athleteDTO.lastName( athlete.getLastName() );
            athleteDTO.country( athlete.getCountry() );
            athleteDTO.ftp( athlete.getFtp() );
        }

        return athleteDTO.build();
    }

    @Override
    public Athlete toPOJO(AthleteDTO athleteDTO) {
        if ( athleteDTO == null ) {
            return null;
        }

        Athlete athlete = new Athlete();

        athlete.setId( athleteDTO.getId() );
        athlete.setFirstName( athleteDTO.getFirstName() );
        athlete.setLastName( athleteDTO.getLastName() );
        athlete.setCountry( athleteDTO.getCountry() );
        athlete.setFtp( athleteDTO.getFtp() );

        return athlete;
    }

    protected PlatformDTO jsonElementUUIDStringStringRecord4ToPlatformDTO(Record4<JsonElement, UUID, String, String> record4) {
        if ( record4 == null ) {
            return null;
        }

        PlatformDTO platformDTO = new PlatformDTO();

        platformDTO.setId( record4.get(1, UUID.class) );
        platformDTO.setCode( record4.get(2, String.class) );
        platformDTO.setName( record4.get(3, String.class) );

        return platformDTO;
    }
}
