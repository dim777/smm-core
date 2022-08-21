package tech.ineb.sport.manager.api.mappers;

import org.mapstruct.Mapper;
import tech.ineb.sport.lib.common.models.dto.PlatformDTO;
import tech.ineb.sport.lib.common.models.tables.pojos.Platform;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
@Mapper(componentModel = "spring")
public interface PlatformMapper {
  PlatformDTO toDTO(Platform platform);
}
