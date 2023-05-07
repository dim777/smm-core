package tech.ineb.sport.manager.api.integration.expiration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.ineb.sport.manager.api.integration.Session;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
@Configuration @Slf4j
public class DefaultExpiration {
  public static final int TIMEOUT_DEFAULT = 300_000;

  /**
   * Check for cache expiration
   *
   * @return isOk
   */
  @Bean Function<Session<?>, Boolean> checkExpiration() {
    return (Function<Session<?>, Boolean>) session -> {
      //check for expiration
      final long diff = getDiff(session);
      return diff > TIMEOUT_DEFAULT;
    };
  }

  /**
   * Get diff in
   *
   * @param s session
   * @return diff in milliseconds
   */
  private long getDiff(Session<?> s) {
    final LocalDateTime created = s.getCreated();
    final LocalDateTime now = LocalDateTime.now();
    return ChronoUnit.MILLIS.between(created, now);
  }
}
