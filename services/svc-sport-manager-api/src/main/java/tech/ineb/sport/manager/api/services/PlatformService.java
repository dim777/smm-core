package tech.ineb.sport.manager.api.services;

import tech.ineb.sport.lib.common.models.dto.PlatformDTO;

import java.util.List;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
public interface PlatformService {
  List<PlatformDTO> findAll();

  PlatformDTO findByCode(String code);
}
