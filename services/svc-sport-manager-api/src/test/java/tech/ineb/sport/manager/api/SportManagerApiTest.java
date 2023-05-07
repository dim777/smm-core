package tech.ineb.sport.manager.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import tech.ineb.sport.manager.api.config.RestConfig;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

import java.time.Duration;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
@ContextConfiguration(
    classes = {RestConfig.class}
)
@Slf4j
public class SportManagerApiTest {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testGooSearch() {
        log.info("RestTemplate = {}", restTemplate);


    }

    @Test
    public void testLoginToStrava() {
        log.info("RestTemplate = {}", restTemplate);

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            String athleteId = "25076752";

            driver.get("https://www.strava.com/login");
            driver.findElement(By.cssSelector(".google .google-button")).click();
            final String emailCssSelector = "input[type=email]";
            wait.until(presenceOfElementLocated(By.cssSelector(emailCssSelector)));
            driver.findElement(By.cssSelector(emailCssSelector)).sendKeys("erohin.da" + Keys.ENTER);
            final String passwordCssSelector = "input[type=password]";
            wait.until(visibilityOfElementLocated(By.cssSelector(passwordCssSelector)));
            driver.findElement(By.cssSelector(passwordCssSelector)).sendKeys("Fl4yoUfl!" + Keys.ENTER);

            wait.until(urlContains("dashboard"));
            final WebElement athleteName = wait.until(presenceOfElementLocated(By.cssSelector(".athlete-name")));
            log.info("athleteName = {}", athleteName.getText());


            //define pagination size

            driver.navigate().to("https://www.strava.com/athletes/" + athleteId + "/follows?type=following");

//      WebElement subscriptions = driver.findElement(By.cssSelector("ul.list-athletes"));
//      List<WebElement> links = subscriptions.findElements(By.tagName("li"));
//
//      for (int i = 1; i < links.size(); i++) {
//        System.out.println(links.get(i).getText());
//      }

            log.info("driver = {}", driver);
        } finally {
            driver.quit();
        }
    }
}