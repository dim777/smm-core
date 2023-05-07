package tech.ineb.sport.manager.api.config;

import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
@Configuration
public class UndertowEmbeddedServletContainerConfig {
  @Value("${server.port}")
  private Integer port;

  @Bean
  public UndertowServletWebServerFactory embeddedServletContainerFactory() {
    UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
    factory.addBuilderCustomizers(builder -> builder.addHttpListener(port, "localhost"));

    //fixme: UT026010: Buffer pool was not set on WebSocketDeploymentInfo, the default pool will be used
    factory.addDeploymentInfoCustomizers(deploymentInfo -> {
      WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
      webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(false, 1024));
      deploymentInfo.addServletContextAttribute("io.undertow.websockets.jsr.WebSocketDeploymentInfo", webSocketDeploymentInfo);
    });

    return factory;
  }
}
