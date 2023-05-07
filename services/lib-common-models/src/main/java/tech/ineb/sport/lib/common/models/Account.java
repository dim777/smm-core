package tech.ineb.sport.lib.common.models;

import lombok.Getter;
import lombok.Setter;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
@Getter
@Setter
public class Account<ID> {
  /**
   * Id in platfrom (i. e. strava athlete id)
   */
  private ID id;
  private String platformCode;
}
