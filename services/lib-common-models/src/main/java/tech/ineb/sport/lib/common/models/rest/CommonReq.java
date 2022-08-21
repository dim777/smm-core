package tech.ineb.sport.lib.common.models.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.UUID;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
@Data
public class CommonReq<T> {
  @ApiModelProperty(notes = "CorrelationId", required = true)
  private UUID correlationId = UUID.randomUUID();

  @ApiModelProperty(notes = "Payload", required = true)
  private T payload;
}
