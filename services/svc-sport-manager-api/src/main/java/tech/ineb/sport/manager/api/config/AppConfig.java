package tech.ineb.sport.manager.api.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
@Configuration @Slf4j
public class AppConfig {
  @Data
  public static class StravaConfig {
    @NotEmpty
    private String url;
    @NotNull
    @PositiveOrZero
    private Integer connectTimeout = 10_000;
    @NotNull
    @PositiveOrZero
    private Integer readTimeout = 10_000;
    @NotNull
    @PositiveOrZero
    private Integer timeout = 10_000;
  }

  @Bean
  public StravaConfig stravaConfig() {
    return new StravaConfig();
  }

  @Bean
  public RestTemplate stravaRestTemplate(StravaConfig stravaConfig) {
    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    requestFactory.setConnectTimeout(stravaConfig.getConnectTimeout());
    requestFactory.setReadTimeout(stravaConfig.getReadTimeout());

    RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(requestFactory));
    restTemplate.setInterceptors(Collections.singletonList(this::interceptor));
    return restTemplate;
  }

  @Bean
  public ExecutorService executor() {
    return Executors.newSingleThreadExecutor();
  }

  //todo: should be refactored
  private ClientHttpResponse interceptor(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    log.debug("Request URI = {}", request.getURI());
    log.debug("Request method = {}", request.getMethod());
    log.debug("Request headers = {}", request.getHeaders());
    log.debug("Request body = {}", new String(body, StandardCharsets.UTF_8));
    ClientHttpResponse response = execution.execute(request, body);
    StringBuilder inputStringBuilder = new StringBuilder();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8));
    String line = bufferedReader.readLine();
    while (line != null) {
      inputStringBuilder.append(line);
      inputStringBuilder.append('\n');
      line = bufferedReader.readLine();
    }
    log.debug("Response status code = {}", response.getStatusCode());
    log.debug("Response status text = {}", response.getStatusText());
    log.debug("Response headers = {}", response.getHeaders());
    log.debug("Response body = {}", inputStringBuilder);
    return response;
  }
}