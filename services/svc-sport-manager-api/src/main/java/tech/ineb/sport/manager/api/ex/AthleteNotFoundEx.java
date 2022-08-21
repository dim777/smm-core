package tech.ineb.sport.manager.api.ex;

import lombok.extern.slf4j.Slf4j;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
@Slf4j
public class AthleteNotFoundEx extends RuntimeException {
  public AthleteNotFoundEx(String message, Throwable cause) {
    super(message, cause);
    log.debug("Get following AthleteNotFoundEx = {}", message);
  }

  public AthleteNotFoundEx(String message) {
    super(message);
    log.debug("Get following AthleteNotFoundEx = {}", message);
  }
}
