package tech.ineb.sport.manager.api.ex;

import lombok.extern.slf4j.Slf4j;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
@Slf4j
public class PlatformNotFoundEx extends RuntimeException {
  public PlatformNotFoundEx(String message, Throwable cause) {
    super(message, cause);
    log.debug("Get following PlatformNotFoundEx = {}", message);
  }

  public PlatformNotFoundEx(String message) {
    super(message);
    log.debug("Get following PlatformNotFoundEx = {}", message);
  }
}
