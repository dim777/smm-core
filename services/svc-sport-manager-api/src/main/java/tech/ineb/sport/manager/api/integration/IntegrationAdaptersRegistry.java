package tech.ineb.sport.manager.api.integration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tech.ineb.sport.manager.api.ex.IntegrationAdapterEx;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class maintains ...
 *
 * @author Dmitriy Erokhin dim777@ya.ru
 */
@Component @Slf4j
public class IntegrationAdaptersRegistry {
  private final Map<PlatformCode<?>, IntegrationAdapter<?>> adapters = new HashMap<>();

  /**
   * Add IntegrationAdapter
   *
   * @param adapter IntegrationAdapter
   * @return registry
   */
  public Map<PlatformCode<?>, IntegrationAdapter<?>> add(IntegrationAdapter<?> adapter) {
    final var clazz = adapter.getClass();
    log.debug("Get following clazz as adapter = {}", clazz.getName());
    adapters.putIfAbsent(PlatformCode.of(adapter.code()), adapter);
    return adapters;
  }

  public <T> IntegrationAdapter<?> findByCode(T code) {
    PlatformCode<T> platformCode = PlatformCode.of(code);
    final IntegrationAdapter<?> integrationAdapter = adapters.get(platformCode);
    if (Objects.isNull(integrationAdapter)) {
      throw new IntegrationAdapterEx("Couldn't find specific adapter by code = " + code);
    }
    log.debug("Get following adapter = '{}'", integrationAdapter);
    return integrationAdapter;
  }

  @Getter @Setter
  @AllArgsConstructor(staticName = "of")
  public static class PlatformCode<T> {
    T code;

    @Override public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      PlatformCode<?> that = (PlatformCode<?>) o;
      return Objects.equals(code, that.code);
    }

    @Override public int hashCode() {
      return Objects.hash(code);
    }
  }
}
