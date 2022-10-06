package com.ebaykorea.payback.infrastructure.persistence.repository;

import org.springframework.beans.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Column annotation 기반의 object Mapper
 * - BeanPropertyRowMapper 코드를 참조
 * - 몇 가지 Debug 및 Mode 옵션 제거
 * - PropertyDescriptor 대신 Field 사용
 * - property 이름의 특정 케이스 고려 불필요하도록 제작
 * - JPA 와 JDBC 사이의 혼종이 된 느낌...???
 */
public class CustomPropertyRowMapper<T> implements RowMapper<T> {
  @Nullable
  private ConversionService conversionService = DefaultConversionService.getSharedInstance();
  @Nullable private Class<T> mappedClass;
  @Nullable private Map<String, Field> fieldToPropertyMap;

  public CustomPropertyRowMapper(Class<T> mappedClass) {
    initialize(mappedClass);
  }

  /**
   * Return a {@link ConversionService} for binding JDBC values to bean properties,
   * or {@code null} if none.
   *
   * @since 4.3
   */
  @Nullable
  public ConversionService getConversionService() {
    return this.conversionService;
  }

  protected void initialize(Class<T> mappedClass) {
    this.mappedClass = mappedClass;
    this.fieldToPropertyMap = Arrays.stream(mappedClass.getDeclaredFields())
        .filter(field -> field.getAnnotation(Column.class) != null)
        .peek(field -> field.setAccessible(true))
        .collect(
            Collectors.toUnmodifiableMap(
                field -> lowerCaseName(field.getAnnotation(Column.class).name()),
                field -> field
            )
        );
    var conversionService = new DefaultConversionService();
    this.conversionService = conversionService;
  }

  protected String lowerCaseName(String name) {
    return name.toLowerCase(Locale.US);
  }

  @Override
  public T mapRow(ResultSet rs, int rowNumber) throws SQLException {
    Assert.state(this.mappedClass != null, "Mapped class was not specified");
    T mappedObject = BeanUtils.instantiateClass(this.mappedClass);
    BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(mappedObject);
    initBeanWrapper(bw);

    ResultSetMetaData rsmd = rs.getMetaData();
    int columnCount = rsmd.getColumnCount();

    for (int index = 1; index <= columnCount; index++) {
      String column = lowerCaseName(JdbcUtils.lookupColumnName(rsmd, index));
      Field field = this.fieldToPropertyMap.getOrDefault(column, null);
      if (field != null) {
        try {
          Object value = JdbcUtils.getResultSetValue(rs, index, field.getType());
          try {
            bw.setPropertyValue(field.getName(), value);
          } catch (TypeMismatchException ex) {
            throw ex;
          }
        } catch (NotWritablePropertyException ex) {
          throw new DataRetrievalFailureException(
              "Unable to map column '" + column + "' to property '" + field.getName() + "'", ex);
        }
      }
    }

    return mappedObject;
  }

  /**
   * Initialize the given BeanWrapper to be used for row mapping.
   * To be called for each row.
   * <p>The default implementation applies the configured {@link ConversionService},
   * if any. Can be overridden in subclasses.
   *
   * @param bw the BeanWrapper to initialize
   * @see #getConversionService()
   * @see BeanWrapper#setConversionService
   */
  protected void initBeanWrapper(BeanWrapper bw) {
    ConversionService cs = getConversionService();
    if (cs != null) {
      bw.setConversionService(cs);
    }
  }

  /**
   * Static factory method to create a new {@code BeanPropertyRowMapper}
   * (with the mapped class specified only once).
   *
   * @param mappedClass the class that each row should be mapped to
   */
  public static <T> CustomPropertyRowMapper<T> newInstance(Class<T> mappedClass) {
    return new CustomPropertyRowMapper<>(mappedClass);
  }
}
