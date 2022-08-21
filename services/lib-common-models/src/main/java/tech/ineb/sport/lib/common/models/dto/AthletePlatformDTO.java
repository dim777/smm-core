package tech.ineb.sport.lib.common.models.dto;

import com.google.gson.JsonElement;
import lombok.Data;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
@Data
public class AthletePlatformDTO {
  private PlatformDTO platform;
  private JsonElement athletePlatformMeta;
}
