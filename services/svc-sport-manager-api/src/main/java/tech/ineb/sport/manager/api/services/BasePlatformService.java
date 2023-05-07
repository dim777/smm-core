package tech.ineb.sport.manager.api.services;

import lombok.extern.slf4j.Slf4j;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.ineb.sport.lib.common.models.dto.PlatformDTO;
import tech.ineb.sport.lib.common.models.tables.daos.PlatformDao;
import tech.ineb.sport.lib.common.models.tables.pojos.Platform;
import tech.ineb.sport.manager.api.ex.PlatformNotFoundEx;
import tech.ineb.sport.manager.api.mappers.PlatformMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
@Service @Slf4j
public class BasePlatformService implements PlatformService {
  private final PlatformDao platformDao;
  private final PlatformMapper mapper;

  public BasePlatformService(DefaultConfiguration configuration, PlatformMapper mapper) {
    this.platformDao = new PlatformDao(configuration);
    this.mapper = mapper;
  }

  @Override @Transactional(readOnly = true)
  public List<PlatformDTO> findAll() {
    final List<Platform> platforms = platformDao.findAll();
    log.debug("Find all platforms = {}", platforms);
    return platforms.stream()
        .map(p -> {
          final PlatformDTO platformDTO = mapper.toDTO(p);
          log.debug("Mapped platform = {} to platformDTO = {}", p, platformDTO);
          return platformDTO;
        })
        .collect(Collectors.toList());
  }

  @Override @Transactional(readOnly = true)
  public PlatformDTO findByCode(String code) {
    final Optional<Platform> platformOp = Optional.ofNullable(platformDao.fetchOneByCode(code));
    log.debug("Find platformOp = {}", platformOp);
    final Platform platform = platformOp
        .orElseThrow(() -> new PlatformNotFoundEx("Couldn't find platfrom by code = " + code));
    final PlatformDTO platformDTO = mapper.toDTO(platform);
    log.debug("Mapped platform to platformDTO = {}", platformDTO);
    return platformDTO;
  }
}
