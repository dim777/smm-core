package tech.ineb.sport.manager.api.ex;

import lombok.extern.slf4j.Slf4j;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
@Slf4j
public class SyncWithPlatformEx extends RuntimeException {
  public SyncWithPlatformEx(String platform, String message, Throwable cause) {
    super(message, cause);
    log.debug("Get ex while platform = {} sync ex SyncWithPlatformEx = {}", platform, message);
  }

  public SyncWithPlatformEx(String platform, String message) {
    super(message);
    log.debug("Get ex while platform = {} sync ex SyncWithPlatformEx = {}", platform, message);
  }
}
