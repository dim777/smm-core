package tech.ineb.sport.manager.api.ex;

import lombok.extern.slf4j.Slf4j;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
@Slf4j
public class AuthWithPlatformEx extends RuntimeException {
  public AuthWithPlatformEx(String message) {
    super(message);
    log.debug("Get ex while sync with platform = {}", message);
  }

  public AuthWithPlatformEx(String platform, String message, Throwable cause) {
    super(message, cause);
    log.debug("Get ex while platform = {} auth ex SyncWithPlatformEx = {}", platform, message);
  }

  public AuthWithPlatformEx(String platform, String message) {
    super(message);
    log.debug("Get ex while platform = {} auth ex SyncWithPlatformEx = {}", platform, message);
  }
}
