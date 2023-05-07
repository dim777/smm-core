package tech.ineb.sport.manager.api.repository;

import org.jooq.Record;
import tech.ineb.sport.lib.common.models.dto.AthleteDTO;
import tech.ineb.sport.lib.common.models.tables.pojos.Athlete;
import tech.ineb.sport.lib.common.models.tables.pojos.AthleteFollowers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
public interface AthleteRepository {
  Optional<AthleteDTO> findById(UUID id);

  /**
   * Find by athlete platform ids and platfrom code
   *
   * @param ids  athlete platform ids
   * @param code platform code
   * @return set of athletes
   */
  List<Athlete> findAllByStravaId(List<Long> ids, String code);

  /**
   * Find by athlete id and platform code
   *
   * @param id   id
   * @param code platform code
   * @return Athlete
   */
  Optional<AthleteDTO> findByIdAndPlatformCode(UUID id, String code);

  /**
   * Find athlete for platform specific id
   *
   * @param id           id
   * @param platformCode platform code
   * @param <T>
   * @return
   */
  <T> Optional<Athlete> findAthleteForPlatformSpecificId(T id, String platformCode);

  /**
   * Save athlete
   *
   * @param athleteDTO athleteDTO
   * @return Athlete
   */
  Athlete save(AthleteDTO athleteDTO);

  AthleteFollowers getOrCreateFollowerRelation(UUID parentId, UUID childId);

  Optional<AthleteFollowers> find(UUID parentId, UUID childId);

  Record save(UUID parentId, UUID childId);
}
