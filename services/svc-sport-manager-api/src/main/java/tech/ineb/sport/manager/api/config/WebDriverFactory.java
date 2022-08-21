package tech.ineb.sport.manager.api.config;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.annotation.Configuration;

import java.net.URL;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
@Configuration @Slf4j
public class WebDriverFactory extends AbstractFactoryBean<WebDriver> {
  public WebDriverFactory() {
    setSingleton(false);
  }

  @Override public Class<?> getObjectType() {
    return WebDriver.class;
  }

  @Override protected WebDriver createInstance() throws Exception {
    final URL url = new URL("http://localhost:4444");
    final ChromeOptions chromeOptions = new ChromeOptions();
    RemoteWebDriver driver = new RemoteWebDriver(url, chromeOptions);
    log.info("Initialized WebDriver = '{}'", driver);
    return driver;
  }


//  @Override protected WebDriver createInstance() throws Exception {
//    ChromeOptions options = new ChromeOptions();
//    options.addArguments("--blink-settings=imagesEnabled=false");
//    options.addArguments("--window-size=1920x1080");
////    options.setCapability("browserVersion", "67");
////    options.setCapability("platformName", "Windows XP");
////    options.addArguments("--remote-debugging-port=8797");
////    options.setExperimentalOption("debuggerAddress", "localhost:8797");
//
////    options.addArguments("--remote-debugging-port=5555");
////    options.setExperimentalOption("useAutomationExtension", false);
////    options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
////
////    final ChromeDriverService.Builder builder = new ChromeDriverService.Builder();
////    builder.usingPort(5555);
////    builder.
//    final ChromeDriver driver = new ChromeDriver(options);
//    return driver;
//  }
}
