package tech.ineb.sport.lib.common.models.rest;

import lombok.Data;

import java.util.UUID;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
@Data
public class AuthReq {
  private UUID athleteId;
  private String username;
  private String password;
}
