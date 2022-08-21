package tech.ineb.sport.manager.api.integration;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
@Data
public class Session<T> {
  private T handler;
  private LocalDateTime created;

  public static <T> Session<T> of(T driver, LocalDateTime created) {
    Session<T> wrapper = new Session<>();
    wrapper.setHandler(driver);
    wrapper.setCreated(created);
    return wrapper;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Session<?> session = (Session<?>) o;
    return Objects.equals(handler, session.handler) && created.isEqual(session.created);
  }

  @Override public int hashCode() {
    return Objects.hash(handler, created);
  }
}
