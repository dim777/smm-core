package tech.ineb.sport.manager.api.mappers;

import org.mapstruct.Mapper;
import tech.ineb.sport.lib.common.models.dto.CredentialsDTO;
import tech.ineb.sport.lib.common.models.rest.AuthReq;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
@Mapper(componentModel = "spring")
public interface AuthMapper {
  CredentialsDTO toDTO(AuthReq authReq);
}
