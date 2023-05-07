package tech.ineb.sport.lib.common.models.dto;

import lombok.Data;

import java.util.UUID;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
@Data
public class PlatformDTO {
  private UUID id;
  private String code;
  private String name;
}
