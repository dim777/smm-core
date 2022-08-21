package tech.ineb.sport.manager.api.integration.strava;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.ineb.sport.lib.common.models.dto.CredentialsDTO;
import tech.ineb.sport.lib.common.strava.model.DetailedAthlete;
import tech.ineb.sport.manager.api.config.WebDriverInitializer;
import tech.ineb.sport.manager.api.ex.SyncWithPlatformEx;
import tech.ineb.sport.manager.api.integration.Session;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

//import javax.annotation.PostConstruct;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
@Component @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) @Slf4j
public class RpaStravaIntegration implements StravaIntegration {
  private final WebDriverWait wait;
  private final WebDriver driver;

  public RpaStravaIntegration(WebDriver driver,
                              WebDriverInitializer.WebDriverConfig webDriverConfig) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, webDriverConfig.getTimeout());
  }

  @Override public Session<WebDriver> auth(CredentialsDTO credentials) {
    try {
      driver.get("https://www.strava.com/login");
      driver.findElement(By.cssSelector(".google .google-button")).click();
      final String emailCssSelector = "input[type=email]";
      wait.until(presenceOfElementLocated(By.cssSelector(emailCssSelector)));
      driver.findElement(By.cssSelector(emailCssSelector)).sendKeys(credentials.getUsername() + Keys.ENTER);
      final String passwordCssSelector = "input[type=password]";
      wait.until(visibilityOfElementLocated(By.cssSelector(passwordCssSelector)));
      driver.findElement(By.cssSelector(passwordCssSelector)).sendKeys(credentials.getPassword() + Keys.ENTER);

      wait.until(urlContains("dashboard"));
      final WebElement athleteName = wait.until(presenceOfElementLocated(By.cssSelector(".athlete-name")));
      log.info("athleteName = {}", athleteName.getText());

      return Session.of(driver(), LocalDateTime.now());
    } catch (Exception ex) {
      throw new SyncWithPlatformEx("Auth failed", "strava", ex);
    }
  }

  @Override
  public List<DetailedAthlete> findFollowersByAthleteID(Long athleteId) {
    log.info("Find followers by AthleteID = '{}'", athleteId);

    try {
      driver.navigate().to("https://www.strava.com/athletes/" + athleteId + "/follows?type=followers");
      final List<DetailedAthlete> athletes = collectAthlete(driver);
      final Integer pages = definePaginationSize(driver);

      IntStream.range(2, pages + 1)
          .forEach(i -> {
            driver.navigate().to("https://www.strava.com/athletes/" + athleteId + "/follows?page=" + i + "&type=followers");
            final List<DetailedAthlete> pagedDetailedAthletes = collectAthlete(driver);
            athletes.addAll(pagedDetailedAthletes);
          });

      return athletes;
    } catch (Exception ex) {
      throw new SyncWithPlatformEx("Failed to receive athlete list", "strava", ex);
    }
  }

  @Override
  public List<DetailedAthlete> findFollowingByAthleteID(Long athleteId) {
    log.info("Find following by AthleteID = '{}'", athleteId);
    try {
      driver.navigate().to("https://www.strava.com/athletes/" + athleteId + "/follows?type=following");
      final List<DetailedAthlete> athletes = collectAthlete(driver);
      final Integer pages = definePaginationSize(driver);

      IntStream.range(2, pages + 1)
          .forEach(i -> {
            driver.navigate().to("https://www.strava.com/athletes/" + athleteId + "/follows?page=" + i + "&type=following");
            final List<DetailedAthlete> pagedDetailedAthletes = collectAthlete(driver);
            athletes.addAll(pagedDetailedAthletes);
          });

      return athletes;
    } catch (Exception ex) {
      driver.quit();
      throw new SyncWithPlatformEx("Failed to receive athlete list", "strava", ex);
    }
  }

  @Override public Boolean cancelSubscription(Long childStravaId) {
    log.info("Cancel subscription for AthleteID = '{}'", childStravaId);
    try {
      driver.navigate().to("https://www.strava.com/athletes/" + childStravaId);

      if (!driver.findElements(By.cssSelector(".follow-action .cancel")).isEmpty()) {
        WebElement unSubscribeBtn = driver.findElement(By.cssSelector(".follow-action .cancel"));
        unSubscribeBtn.click();
      }

      if (!driver.findElements(By.cssSelector(".follow-action .unfollow")).isEmpty()) {
        WebElement unSubscribeBtn = driver.findElement(By.cssSelector(".follow-action .unfollow"));
        unSubscribeBtn.click();
      }

      //todo: should be successfully send
      return Boolean.TRUE;
    } catch (Exception ex) {
      driver.quit();
      throw new SyncWithPlatformEx("Failed to cancel subscription", "strava", ex);
    }
  }

  /**
   * Define total page count
   *
   * @param driver
   * @return
   */
  private Integer definePaginationSize(WebDriver driver) {
    WebElement pagination = driver.findElement(By.cssSelector("ul.pagination"));

    final Integer pages = pagination.findElements(By.tagName("li"))
        .stream()
        .filter(el -> {
          final String classAttribute = el.getAttribute("class");
          return !classAttribute.contains("previous_page")
              && !classAttribute.contains("next_page")
              && !classAttribute.contains("gap");
        })
        .map(el -> {
          final String classAttribute = el.getAttribute("class");
          if (classAttribute.contains("active")) {
            final WebElement elSpan = el.findElement(By.cssSelector("span"));
            final String elValue = elSpan.getText();
            return Integer.parseInt(elValue);
          }

          final WebElement elLink = el.findElement(By.cssSelector("a"));
          final String elValue = elLink.getText();
          return Integer.parseInt(elValue);
        })
        .max(Integer::compareTo)
        .orElseThrow(() -> new SyncWithPlatformEx("strava", "couldn't get page count"));

    log.debug("Number of pages = {}", pages);
    return pages;
  }

  private List<DetailedAthlete> collectAthlete(WebDriver driver) {
    WebElement followers = driver.findElement(By.cssSelector("ul.list-athletes"));
    return followers.findElements(By.tagName("li"))
        .stream()
        .filter(el -> !Objects.isNull(el.getAttribute("data-athlete-id")))
        .map(el -> {
          final String id = el.getAttribute("data-athlete-id");
          final WebElement elName = el.findElement(By.cssSelector(".text-callout>a"));
          final String[] splitName = elName.getText().split("\\s+");

          DetailedAthlete athlete = new DetailedAthlete();
          athlete.setId(Long.parseLong(id));
          athlete.setFirstname(splitName[0]);
          athlete.setLastname(splitName[1]);

          log.debug("Mapped to athlete = {}", athlete);
          return athlete;
        })
        .peek(a -> {
          log.debug("Get following athlete = '{}'", a);
        })
        .collect(Collectors.toList());
  }

  @Lookup
  public WebDriver driver() {
    return null;
  }
}
