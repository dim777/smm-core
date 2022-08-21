package tech.ineb.sport.manager.api.repository;

import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.ineb.sport.lib.common.models.dto.AthleteDTO;
import tech.ineb.sport.lib.common.models.dto.AthletePlatformDTO;
import tech.ineb.sport.lib.common.models.tables.daos.AthleteDao;
import tech.ineb.sport.lib.common.models.tables.daos.AthleteFollowersDao;
import tech.ineb.sport.lib.common.models.tables.daos.AthletePlatformDao;
import tech.ineb.sport.lib.common.models.tables.pojos.Athlete;
import tech.ineb.sport.lib.common.models.tables.pojos.AthleteFollowers;
import tech.ineb.sport.lib.common.models.tables.pojos.AthletePlatform;
import tech.ineb.sport.manager.api.mappers.AthleteMapper;
import tech.ineb.sport.manager.api.mappers.PlatformMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static tech.ineb.sport.lib.common.models.tables.Athlete.ATHLETE;
import static tech.ineb.sport.lib.common.models.tables.AthletePlatform.ATHLETE_PLATFORM;
import static tech.ineb.sport.lib.common.models.tables.Platform.PLATFORM;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
@Service @Slf4j
public class BaseAthleteRepository implements AthleteRepository {
  private final DSLContext dsl;
  private final AthleteMapper athleteMapper;
  private final PlatformMapper platformMapper;
  private final AthleteDao athleteDao;
  private final AthletePlatformDao athletePlatformDao;
  private final AthleteFollowersDao athleteFollowersDao;

  public BaseAthleteRepository(AthleteMapper athleteMapper, PlatformMapper platformMapper, DSLContext dsl) {
    this.dsl = dsl;
    this.athleteMapper = athleteMapper;
    this.platformMapper = platformMapper;
    this.athleteDao = new AthleteDao(dsl.configuration());
    this.athletePlatformDao = new AthletePlatformDao(dsl.configuration());
    this.athleteFollowersDao = new AthleteFollowersDao(dsl.configuration());
  }

  @Transactional(readOnly = true)
  @Override public Optional<AthleteDTO> findById(UUID athleteId) {
    log.debug("DSL FindByIdAndPlatformCode => id='{}'", athleteId);

    final Optional<AthleteDTO> athleteOp = dsl.select(
        ATHLETE.ID,
        ATHLETE.FIRST_NAME,
        ATHLETE.LAST_NAME,
        ATHLETE.COUNTRY,
        ATHLETE.FTP,
        ATHLETE.VERSION
    )
        .from(ATHLETE)
        .where(ATHLETE.ID.eq(athleteId))
        .fetchOptional()
        .map(record -> {
          final AthleteDTO a = athleteMapper.toDTO(record);
          log.debug("Mapped to AthleteDTO = '{}'", a);
          return a;
        });

    athleteOp.map(a -> {
      final List<AthletePlatformDTO> athletePlatforms = dsl.select(
          ATHLETE_PLATFORM.ATHLETE_PLATFORM_META,
          PLATFORM.ID,
          PLATFORM.CODE,
          PLATFORM.NAME
      )
          .from(ATHLETE_PLATFORM)
          .leftJoin(PLATFORM).on(ATHLETE_PLATFORM.PLATFORM_ID.eq(PLATFORM.ID))
          .where(ATHLETE_PLATFORM.ATHLETE_ID.eq(athleteId))
          .fetch()
          .map(record -> {
            final AthletePlatformDTO ap = athleteMapper.toDTO(record);
            log.debug("Mapped to AthletePlatformDTO = '{}'", ap);
            return ap;
          });
      //todo: fix it
      a.setPlatforms(new HashSet<>(athletePlatforms));
      return a;
    });

    return athleteOp;
  }

  @Transactional(readOnly = true)
  @Override
  public List<Athlete> findAllByStravaId(List<Long> ids, String platformCode) {
    log.debug("DSL FindAllByStravaId => ids='{}', code='{}'", ids, platformCode);

    final String idsIns = ids.stream()
        .map(String::valueOf)
        .collect(Collectors.joining("','", "'", "'"));

    return dsl.select()
        .from(ATHLETE)
        .leftJoin(ATHLETE_PLATFORM)
        .on(ATHLETE_PLATFORM.ATHLETE_ID.eq(ATHLETE.ID))
        .leftJoin(PLATFORM)
        .on(PLATFORM.ID.eq(ATHLETE_PLATFORM.PLATFORM_ID))
        .where(PLATFORM.CODE.eq(platformCode).and("athlete_platform_meta ->> 'id' IN (" + idsIns + ")"))
        .fetch()
        .into(Athlete.class);
  }

  @Transactional(readOnly = true)
  @Override
  public Optional<AthleteDTO> findByIdAndPlatformCode(UUID id, String platformCode) {
    log.debug("DSL FindByIdAndPlatformCode => id='{}', code='{}'", id, platformCode);
    return dsl.select(
        ATHLETE.ID,
        ATHLETE.FIRST_NAME,
        ATHLETE.LAST_NAME,
        ATHLETE.COUNTRY,
        ATHLETE.FTP,
        ATHLETE_PLATFORM.ATHLETE_PLATFORM_META,
        PLATFORM.ID,
        PLATFORM.CODE,
        PLATFORM.NAME,
        ATHLETE.VERSION
    )
        .from(ATHLETE)
        .leftJoin(ATHLETE_PLATFORM)
        .on(ATHLETE_PLATFORM.ATHLETE_ID.eq(ATHLETE.ID))
        .leftJoin(PLATFORM)
        .on(PLATFORM.ID.eq(ATHLETE_PLATFORM.PLATFORM_ID))
        .where(PLATFORM.CODE.eq(platformCode)
            .and(ATHLETE.ID.eq(id)))
        .fetchOptional()
        .map(record -> {
          final AthleteDTO a = athleteMapper.toDTO(record);
          log.debug("Mapped to AthleteDTO = '{}'", a);
          return a;
        });
  }

  @Transactional @Override public Athlete save(AthleteDTO athleteDTO) {
    final Athlete athlete = athleteMapper.toPOJO(athleteDTO);
    athleteDao.insert(athlete);

    final List<AthletePlatform> athletePlatforms = athleteDTO.getPlatforms().stream()
        .map(p -> {
          AthletePlatform ap = new AthletePlatform();
          ap.setAthleteId(athleteDTO.getId());
          ap.setPlatformId(p.getPlatform().getId());
          ap.setAthletePlatformMeta(p.getAthletePlatformMeta());
          return ap;
        })
        .collect(Collectors.toList());

    athletePlatformDao.insert(athletePlatforms);

    final Athlete a = athleteDao.findById(athlete.getId());
    log.debug("Saved athlete = '{}'", a);
    return a;
  }

  //todo: should be decomposed
  @Transactional @Override
  public AthleteFollowers getOrCreateFollowerRelation(UUID parentId, UUID childId) {
    final DSLContext dsl = athleteDao.configuration().dsl();
    log.debug("DSL getOrCreateFollowerRelation => parentId='{}', childId='{}'", parentId, childId);
    return dsl.select()
        .from(tech.ineb.sport.lib.common.models.tables.AthleteFollowers.ATHLETE_FOLLOWERS)
        .where(tech.ineb.sport.lib.common.models.tables.AthleteFollowers.ATHLETE_FOLLOWERS.ATHLETE_ID.eq(parentId)
            .and(tech.ineb.sport.lib.common.models.tables.AthleteFollowers.ATHLETE_FOLLOWERS.ATHLETE_FOLLOWER_ID.eq(childId)))
        .fetchOptional()
        .orElseGet(() -> save(parentId, childId))
        .into(AthleteFollowers.class);
  }

  @Transactional(readOnly = true)
  @Override
  public Optional<AthleteFollowers> find(UUID parentId, UUID childId) {
    log.debug("DSL find => parentId='{}', childId='{}'", parentId, childId);
    return dsl.select()
        .from(tech.ineb.sport.lib.common.models.tables.AthleteFollowers.ATHLETE_FOLLOWERS)
        .where(tech.ineb.sport.lib.common.models.tables.AthleteFollowers.ATHLETE_FOLLOWERS.ATHLETE_ID.eq(parentId)
            .and(tech.ineb.sport.lib.common.models.tables.AthleteFollowers.ATHLETE_FOLLOWERS.ATHLETE_FOLLOWER_ID.eq(childId)))
        .fetchOptional()
        .map(r -> r.into(AthleteFollowers.class));
  }

  @Transactional @Override public Record save(UUID parentId, UUID childId) {
    AthleteFollowers af = new AthleteFollowers();
    af.setAthleteId(parentId);
    af.setAthleteFollowerId(childId);
    athleteFollowersDao.insert(af);

    return dsl.select()
        .from(tech.ineb.sport.lib.common.models.tables.AthleteFollowers.ATHLETE_FOLLOWERS)
        .where(tech.ineb.sport.lib.common.models.tables.AthleteFollowers.ATHLETE_FOLLOWERS.ATHLETE_ID.eq(parentId)
            .and(tech.ineb.sport.lib.common.models.tables.AthleteFollowers.ATHLETE_FOLLOWERS.ATHLETE_FOLLOWER_ID.eq(childId)))
        .fetchOne();
  }

  @Transactional(readOnly = true)
  @Override
  public <T> Optional<Athlete> findAthleteForPlatformSpecificId(T id, String platformCode) {
    log.debug("DSL findAthleteForPlatformSpecificId => id='{}', code='{}'", id, platformCode);
    return dsl.select(
        ATHLETE.ID,
        ATHLETE.FIRST_NAME,
        ATHLETE.LAST_NAME,
        ATHLETE.COUNTRY,
        ATHLETE.FTP,
        ATHLETE.VERSION
    )
        .from(ATHLETE)
        .leftJoin(ATHLETE_PLATFORM)
        .on(ATHLETE_PLATFORM.ATHLETE_ID.eq(ATHLETE.ID))
        .leftJoin(PLATFORM)
        .on(PLATFORM.ID.eq(ATHLETE_PLATFORM.PLATFORM_ID))
        .where(PLATFORM.CODE.eq(platformCode).and("athlete_platform_meta ->> 'id' = ('" + id + "')"))
        .fetchOptional()
        .map(r -> r.into(Athlete.class));
  }
}
