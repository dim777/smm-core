package tech.ineb.sport.manager.api.mappers;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.jooq.Record10;
import org.jooq.Record4;
import org.jooq.Record6;
import org.mapstruct.*;
import tech.ineb.sport.lib.common.models.dto.AthleteDTO;
import tech.ineb.sport.lib.common.models.dto.AthletePlatformDTO;
import tech.ineb.sport.lib.common.models.dto.PlatformDTO;
import tech.ineb.sport.lib.common.models.tables.pojos.Athlete;
import tech.ineb.sport.lib.common.strava.model.DetailedAthlete;

import java.util.UUID;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
@Mapper(componentModel = "spring")
public interface AthleteMapper {
  /**
   * ATHLETE.ID,
   * ATHLETE.FIRST_NAME,
   * ATHLETE.LAST_NAME,
   * ATHLETE.COUNTRY,
   * ATHLETE.FTP,
   * ATHLETE.VERSION
   *
   * @param record
   * @return
   */
  @Mapping(target = "id", expression = "java( record.get(0, UUID.class))")
  @Mapping(target = "lastName", expression = "java( record.get(2, String.class) )")
  @Mapping(target = "country", expression = "java( record.get(3, String.class) )")
  @Mapping(target = "ftp", expression = "java( record.get(4, Integer.class) )")
  AthleteDTO toDTO(Record6<UUID, String, String, String, Integer, Integer> record);

  @Mapping(target = "athletePlatformMeta", expression = "java( record.get(0, JsonElement.class) )")
  @Mapping(target = "platform.id", expression = "java( record4.get(1, UUID.class) )")
  @Mapping(target = "platform.code", expression = "java( record4.get(2, String.class) )")
  @Mapping(target = "platform.name", expression = "java( record4.get(3, String.class) )")
  AthletePlatformDTO toDTO(Record4<JsonElement, UUID, String, String> record);

  /**
   * ATHLETE.ID,
   * ATHLETE.FIRST_NAME,
   * ATHLETE.LAST_NAME,
   * ATHLETE.COUNTRY,
   * ATHLETE.FTP,
   * ATHLETE_PLATFORM.ATHLETE_PLATFORM_META,
   * PLATFORM.ID,
   * PLATFORM.CODE,
   * PLATFORM.NAME,
   * ATHLETE.VERSION
   *
   * @param record active record
   * @return athleteDTO
   */
  @Mapping(target = "id", expression = "java( record.get(0, UUID.class) )")
  @Mapping(target = "firstName", expression = "java( record.get(1, String.class) )")
  @Mapping(target = "lastName", expression = "java( record.get(2, String.class) )")
  @Mapping(target = "country", expression = "java( record.get(3, String.class) )")
  @Mapping(target = "ftp", expression = "java( record.get(4, Integer.class) )")
//  @Mapping(target = "athletePlatformMeta", expression = "java( record.get(5) )")
//  @Mapping(target = "platform", expression = "java( record.get(6) )")
//  @Mapping(target = "platform.code", expression = "java( record.get(7) )")
//  @Mapping(target = "platform.name", expression = "java( record.get(8) )")
  AthleteDTO toDTO(Record10<UUID, String, String, String, Integer, JsonElement, UUID, String, String, Integer> record);

  @Mapping(target = "id", ignore = true)
  @Mapping(source = "detailedAthlete.lastname", target = "lastName")
  @Mapping(source = "detailedAthlete.firstname", target = "firstName")
//  @Mapping(source = "detailedAthlete.id", target = "athletePlatformMeta", qualifiedByName = "MetaData")
  @BeanMapping(qualifiedByName = {"DefaultID"})
  AthleteDTO toDTO(DetailedAthlete detailedAthlete, PlatformDTO platform);

  @Mapping(source = "athlete.id", target = "id")
//  @Mapping(target = "athletePlatformMeta", ignore = true)
  AthleteDTO toDTO(Athlete athlete, PlatformDTO platform);

  @Mapping(target = "version", ignore = true)
  Athlete toPOJO(AthleteDTO athleteDTO);

  @AfterMapping
  @Named("DefaultID")
  default void defaultId(@MappingTarget AthleteDTO.AthleteDTOBuilder athleteDTOBuilder) {
    athleteDTOBuilder.id(UUID.randomUUID());
  }

  @Named("MetaData")
  default JsonElement metaData(Long id) {
    JsonParser parser = new JsonParser();
    return parser.parse("{\"id\":\"" + id + "\"}");
  }
}
