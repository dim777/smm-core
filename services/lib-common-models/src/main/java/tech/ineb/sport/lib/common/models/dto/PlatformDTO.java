package tech.ineb.sport.lib.common.models.dto;

import lombok.Data;

import java.util.UUID;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
@Data
public class PlatformDTO {
  private UUID id;
  private String code;
  private String name;
}
