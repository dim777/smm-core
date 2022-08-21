package tech.ineb.sport.manager.api.config;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
@Configuration
public class WebDriverInitializer {
  @Data
  public static class WebDriverConfig {
    //timeout in seconds
    @NotNull @PositiveOrZero private Long timeout = 30L;
  }

  @Bean
  WebDriverConfig webDriverConfig() {
    return new WebDriverConfig();
  }
}
