package com.ebaykorea.payback.infrastructure.persistence.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class CustomSqlParameterConverter {
  /**
   * instance 의 field 값을 columnName 을 기준으로 SqlParameterSource 로 변환하는 Collector
   *
   * @param instance 객체
   * @return {MapSqlParameterSource}
   */
  public MapSqlParameterSource convert(Object instance, Map<String, Field> fieldMap) {
    return new MapSqlParameterSource(fieldMap.entrySet().stream()
        .map(item -> {
          try {
            final var columnName = item.getKey();
            final var field = item.getValue();
            final var value = convertValue(field.get(instance));

            return new AbstractMap.SimpleImmutableEntry(
                columnName,
                value
            );
          } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
          }
        })
        .collect(HashMap::new, (m, v) -> m.put(v.getKey().toString(), v.getValue()), HashMap::putAll)
    );
  }

  /** column 의 value 값 변경
   * Instant type -> Timestamp
   * @param value
   * @return
   */
  private Object convertValue(final Object value) {
    return Optional.ofNullable(value)
        .map(convertTimestamp())
        .orElse(null);
  }

  private Function<Object, Object> convertTimestamp() {
    return value -> value.getClass().equals(Instant.class) ?
        Timestamp.from((Instant) value) :
        value;
  }
}
