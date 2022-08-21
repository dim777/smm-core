package tech.ineb.sport.manager.api.integration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import tech.ineb.sport.lib.common.models.dto.CredentialsDTO;
import tech.ineb.sport.manager.api.ex.AuthWithPlatformEx;
import tech.ineb.sport.manager.api.services.AthletesService;

import java.util.Optional;
import java.util.UUID;

/**
 * This class maintains ...
 * todo: should be scheduler for cache
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
@AllArgsConstructor @Aspect @Component @Slf4j
public class AuthRegistryAspect {
  private final IntegrationAdaptersRegistry registry;
  private final AthletesService athletesService;
  private final SessionRegistry sessionRegistry;

  @Pointcut("execution(* tech.ineb.sport.manager.api.integration.IntegrationAdapter.auth(..))")
  public void authPointcut() {
  }

  @Pointcut("execution(* tech.ineb.sport.manager.api.integration.IntegrationAdapter.*(..))")
  public void allMethodsPointcut() {
  }

  @Around("authPointcut()")
  public Object authMethodBySessionRegistry(ProceedingJoinPoint point) {
    //get athleteId
    final CredentialsDTO credentialsDTO = (CredentialsDTO) point.getArgs()[0];
    final String platformCode = (String) point.getArgs()[1];
    final UUID athleteId = credentialsDTO.getAthleteId();

    final Optional<Session<?>> sessionOp =
        sessionRegistry.findSessionByAthleteIdAndPlatformCode(athleteId, platformCode);
    if (sessionOp.isEmpty()) {
      Session<?> s = auth(point, platformCode);
      return sessionRegistry.put(athleteId, platformCode, s);
    }
    return sessionOp.get();
  }

  @Around("allMethodsPointcut() && ! authPointcut()")
  public Object authAllMethodsBySessionRegistry(ProceedingJoinPoint point) {
    //get athleteId
    final CredentialsDTO credentialsDTO = (CredentialsDTO) point.getArgs()[0];
    final String platformCode = (String) point.getArgs()[1];
    final UUID athleteId = credentialsDTO.getAthleteId();

    final Optional<Session<?>> sessionOp =
        sessionRegistry.findSessionByAthleteIdAndPlatformCode(athleteId, platformCode);

    if (sessionOp.isPresent()) {
      throw new AuthWithPlatformEx("Athlete with id = " + athleteId + "should be auth first");
    }

    Object retValue;
    try {
      retValue = point.proceed();
    } catch (Throwable throwable) {
      throw new AuthWithPlatformEx(platformCode, "failed to make destination request");
    }
    return retValue;
  }

  /**
   * Auth
   *
   * @param point        point
   * @param platformName platformName
   * @return session
   */
  private Session<?> auth(ProceedingJoinPoint point, String platformName) {
    Session<?> session = proceed(point, platformName);
    log.debug("After invoking auth() method. Return value = '{}'", session);
    return session;
  }

  /**
   * Proceed to original point
   *
   * @param point        original point
   * @param platformName platform name
   * @return OK/KO
   */
  private Session<?> proceed(ProceedingJoinPoint point, String platformName) {
    Session<?> session;
    try {
      session = (Session<?>) point.proceed();
    } catch (Throwable throwable) {
      throw new AuthWithPlatformEx(platformName, "failed to make auth request");
    }
    return session;
  }
}
