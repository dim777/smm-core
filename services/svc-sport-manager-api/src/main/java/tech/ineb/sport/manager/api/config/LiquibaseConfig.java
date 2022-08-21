package tech.ineb.sport.manager.api.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {
  @Profile("!local-test")
  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.liquibase")
  public SpringLiquibase liquibase(DataSource dataSource) {
    SpringLiquibase springLiquibase = new SpringLiquibase();
    springLiquibase.setDataSource(dataSource);
    return springLiquibase;
  }
}
