package tech.ineb.sport.manager.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
@SpringBootApplication
@Slf4j
public class SportManagerApi {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(SportManagerApi.class, args);
        log.info("Application {} is loaded in {}.", ctx.getApplicationName(), LocalDateTime.now());
    }
}
