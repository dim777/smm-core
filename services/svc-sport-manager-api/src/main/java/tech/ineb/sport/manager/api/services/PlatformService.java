package tech.ineb.sport.manager.api.services;

import tech.ineb.sport.lib.common.models.dto.PlatformDTO;

import java.util.List;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
public interface PlatformService {
  List<PlatformDTO> findAll();

  PlatformDTO findByCode(String code);
}
