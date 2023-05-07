package tech.ineb.sport.manager.api.config;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
@Configuration
public class WebDriverInitializer {
    @Data
    public static class WebDriverConfig {
        //timeout in seconds
        @NotNull
        @PositiveOrZero
        private Long timeout = 30L;
    }

    @Bean
    WebDriverConfig webDriverConfig() {
        return new WebDriverConfig();
    }
}
