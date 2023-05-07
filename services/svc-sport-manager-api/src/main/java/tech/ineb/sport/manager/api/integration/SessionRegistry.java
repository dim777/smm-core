package tech.ineb.sport.manager.api.integration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

/**
 * This class maintains ...
 * todo session registry should be migrated to interface with scheduled cache purge
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
@AllArgsConstructor @Component @Slf4j
public class SessionRegistry {
  private final Map<UUID, Map<String, Session<?>>> sessionRegistry = new HashMap<>();
  private final Function<Session<?>, Boolean> checkExpiration;

  /**
   * Find session by athleteId and platformCode
   *
   * @param athleteId    athleteId
   * @param platformCode platformCode
   * @return session
   */
  public Optional<Session<?>> findSessionByAthleteIdAndPlatformCode(UUID athleteId, String platformCode) {
    final Optional<Session<?>> sessionOp = checkSessionExists(athleteId, platformCode);
    if (sessionOp.isPresent()) {
      final Session<?> s = sessionOp.get();
      if (checkExpiration.apply(s)) {
        return Optional.empty();
      }
    }
    return sessionOp;
  }

  /**
   * Put session into registry
   *
   * @param athleteId    athlete id
   * @param platformCode code of platform
   * @param session      session
   * @return session (new or existing)
   */
  public Session<?> put(UUID athleteId, String platformCode, final Session<?> session) {
    final Optional<Session<?>> sessionOp = checkSessionExists(athleteId, platformCode);
    if (sessionOp.isPresent()) {
      final Session<?> s = sessionOp.get();
      log.info("Session = '{}' already defined for athleteId = '{}' - let's check expiration", s, athleteId);
      //check for expiration
      if (checkExpiration.apply(s)) {
        final Map<String, Session<?>> sessionMap = sessionRegistry.get(athleteId);
        sessionMap.put(platformCode, session);
        return session;
      }
      return s;
    }
    sessionRegistry.put(athleteId, new HashMap<>() {{
          put(platformCode, session);
        }}
    );
    return session;
  }

  private Optional<Session<?>> checkSessionExists(UUID athleteId, String platformCode) {
    if (sessionRegistry.containsKey(athleteId)) {
      final Map<String, Session<?>> sessionMap = sessionRegistry.get(athleteId);
      final Session<?> s = sessionMap.get(platformCode);
      log.debug("Get following session by athleteId = {} and platformCode = {}", athleteId, platformCode);
      return Optional.of(s);
    }
    log.debug("Couldn't find session by athleteId = {}", athleteId);
    return Optional.empty();
  }

}
