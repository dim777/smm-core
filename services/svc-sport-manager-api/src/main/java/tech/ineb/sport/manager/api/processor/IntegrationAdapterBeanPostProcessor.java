package tech.ineb.sport.manager.api.processor;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import tech.ineb.sport.manager.api.integration.IntegrationAdapter;
import tech.ineb.sport.manager.api.integration.IntegrationAdaptersRegistry;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin d.erokhin@corp.mail.ru
 */
@AllArgsConstructor @Configuration @Slf4j
public class IntegrationAdapterBeanPostProcessor implements BeanPostProcessor {
  private final ApplicationContext context;

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    Class<?> clazz = bean.getClass();
    if (bean instanceof IntegrationAdapter) {
      final IntegrationAdaptersRegistry adaptersRegistry = context.getBean(IntegrationAdaptersRegistry.class);
      final IntegrationAdapter<?> adapter = (IntegrationAdapter<?>) bean;
      log.info("Add adapter to registry = '{}'", adapter);
      adaptersRegistry.add(adapter);
    }
    return bean;
  }
}
