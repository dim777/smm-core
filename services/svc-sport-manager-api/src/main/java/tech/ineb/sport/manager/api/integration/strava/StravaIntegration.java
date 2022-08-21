package tech.ineb.sport.manager.api.integration.strava;

import org.openqa.selenium.WebDriver;
import tech.ineb.sport.lib.common.models.dto.CredentialsDTO;
import tech.ineb.sport.lib.common.strava.model.DetailedAthlete;
import tech.ineb.sport.manager.api.integration.Session;

import java.util.List;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
public interface StravaIntegration {
  Session<WebDriver> auth(CredentialsDTO credentials);

  List<DetailedAthlete> findFollowersByAthleteID(Long id);

  List<DetailedAthlete> findFollowingByAthleteID(Long id);

  Boolean cancelSubscription(Long childStravaId);
}
