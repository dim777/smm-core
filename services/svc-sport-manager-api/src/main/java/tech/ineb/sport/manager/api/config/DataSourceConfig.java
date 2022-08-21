package tech.ineb.sport.manager.api.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import tech.ineb.sport.manager.api.listeners.ExceptionTranslator;

@Configuration
@PropertySource(value = "classpath:database.properties")
public class DataSourceConfig {
  private final String jdbcUrl;
  private final String username;
  private final String password;
  private final String driverClassName;
  private final Integer minimumIdle;
  private final Integer maximumPoolSize;
  private final Integer idleTimeout;
  private final String poolName;
  private final Integer maxLifetime;
  private final Integer connectionTimeout;

  public DataSourceConfig(@Value("${hikari.jdbcUrl}") String jdbcUrl,
                          @Value("${hikari.username}") String username,
                          @Value("${hikari.password}") String password,
                          @Value("${hikari.driverClassName}") String driverClassName,
                          @Value("${hikari.minimumIdle}") Integer minimumIdle,
                          @Value("${hikari.maximumPoolSize}") Integer maximumPoolSize,
                          @Value("${hikari.idleTimeout}") Integer idleTimeout,
                          @Value("${hikari.poolName}") String poolName,
                          @Value("${hikari.maxLifetime}") Integer maxLifetime,
                          @Value("${hikari.connectionTimeout}") Integer connectionTimeout) {
    this.jdbcUrl = jdbcUrl;
    this.username = username;
    this.password = password;
    this.driverClassName = driverClassName;
    this.minimumIdle = minimumIdle;
    this.maximumPoolSize = maximumPoolSize;
    this.idleTimeout = idleTimeout;
    this.poolName = poolName;
    this.maxLifetime = maxLifetime;
    this.connectionTimeout = connectionTimeout;
  }

  @Bean
  public HikariConfig hikariConfig() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(jdbcUrl);
    config.setUsername(username);
    config.setPassword(password);
    config.setDriverClassName(driverClassName);
    config.setMinimumIdle(minimumIdle);
    config.setMaximumPoolSize(maximumPoolSize);
    config.setIdleTimeout(idleTimeout);
    config.setPoolName(poolName);
    config.setMaxLifetime(maxLifetime);
    config.setConnectionTimeout(connectionTimeout);
    return config;
  }

  @Bean
  public TransactionAwareDataSourceProxy dataSource() {
    return new TransactionAwareDataSourceProxy(new HikariDataSource(hikariConfig()));
  }

  @Bean
  @Primary
  public DataSourceTransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource());
  }

  @Bean
  public DataSourceConnectionProvider connectionProvider() {
    return new DataSourceConnectionProvider(dataSource());
  }

  @Bean
  public ExceptionTranslator exceptionTransformer() {
    return new ExceptionTranslator();
  }

  @Bean
  public DefaultConfiguration configuration() {
    Settings settings = new Settings().withExecuteWithOptimisticLocking(true);
    DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
    jooqConfiguration.set(connectionProvider());
    jooqConfiguration.set(new DefaultExecuteListenerProvider(exceptionTransformer()));
    jooqConfiguration.set(SQLDialect.POSTGRES);
    jooqConfiguration.set(settings);
    return jooqConfiguration;
  }

  @Bean
  public DefaultDSLContext dsl() {
    return new DefaultDSLContext(configuration());
  }
}
