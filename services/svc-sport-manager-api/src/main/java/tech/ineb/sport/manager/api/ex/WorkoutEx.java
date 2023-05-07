package tech.ineb.sport.manager.api.ex;

import lombok.extern.slf4j.Slf4j;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
@Slf4j
public class WorkoutEx extends RuntimeException {
  public WorkoutEx(String message, Throwable cause) {
    super(message, cause);
    log.debug("Get following WorkoutEx = {}", message);
  }
}
