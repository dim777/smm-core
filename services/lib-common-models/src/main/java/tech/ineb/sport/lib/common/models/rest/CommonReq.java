package tech.ineb.sport.lib.common.models.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.UUID;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
@Data
public class CommonReq<T> {
  @ApiModelProperty(notes = "CorrelationId", required = true)
  private UUID correlationId = UUID.randomUUID();

  @ApiModelProperty(notes = "Payload", required = true)
  private T payload;
}
