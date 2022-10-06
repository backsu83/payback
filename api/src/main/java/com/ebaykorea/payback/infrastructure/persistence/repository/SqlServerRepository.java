package com.ebaykorea.payback.infrastructure.persistence.repository;

import com.ebaykorea.payback.util.PaybackCollections;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.core.ResolvableType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

//TODO: Platform engineering 팀에서 제공하는것으로 대체 가능한지 확인
public abstract class SqlServerRepository<T, ID> {
  protected static final String DEFAULT_RESULT_MAP_ID = "#result-set-1";

  @Getter(AccessLevel.PROTECTED)
  protected JdbcTemplate jdbcTemplate;
  protected CustomSqlParameterConverter sqlParameterConverter;
  protected CustomPropertyRowMapper<?> propertyRowMapper;

  // SP
  private SimpleJdbcCall spFindById;
  private Map<String, SimpleJdbcCall> spFindAllByMap;
  private Map<String, SimpleJdbcCall> spSaveMap;
  private SimpleJdbcCall spSaveReturnsKey;

  // Meta
  private Map<String, Field> entityIds;
  private Map<String, Field> entityColumns;
  private Map<String, Field> idClassFields = Map.of();

  private boolean hasIdClass;

  protected SqlServerRepository(
      final DataSource dataSource
  ) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);

    final var repository = ResolvableType.forInstance(this).as(SqlServerRepository.class);
    final var entityClass = Objects.requireNonNull(repository.resolveGeneric(0));
    final var idClass = Objects.requireNonNull(repository.resolveGeneric(1));

    this.sqlParameterConverter = new CustomSqlParameterConverter();
    this.propertyRowMapper = CustomPropertyRowMapper.newInstance(entityClass);

    initialize(entityClass, idClass);
  }

  private void initialize(final Class<?> entityClass, final Class<?> idClass) {
    this.spFindById = getFindByIdProcedure();
    this.spFindAllByMap = getFindAllByProcedures();
    this.spSaveMap = getSaveProcedures();
    this.spSaveReturnsKey = getSaveReturnsKeyProcedure();
    this.entityColumns = getColumns(entityClass);
    this.entityIds = entityColumns.entrySet().stream()
        .filter(entry -> entry.getValue().getAnnotation(Id.class) != null)
        .collect(
            Collectors.toUnmodifiableMap(
                Map.Entry::getKey,
                Map.Entry::getValue
            )
        );
    this.hasIdClass = hasIdClass(idClass);
    if (hasIdClass) {
      this.idClassFields = getColumns(idClass);
    }
  }

  protected SimpleJdbcCall getFindByIdProcedure() {
    return null;
  }

  protected Map<String, SimpleJdbcCall> getFindAllByProcedures() { return null; }

  protected SimpleJdbcCall getSaveReturnsKeyProcedure() {
    return null;
  }

  protected Map<String,SimpleJdbcCall> getSaveProcedures() { return null; }

  protected MapSqlParameterSource convertToEntityParameter(T t) {
    return sqlParameterConverter.convert(t, entityColumns);
  }

  protected MapSqlParameterSource convertToIdParameter(ID id) {
    if (hasIdClass) {
      return sqlParameterConverter.convert(id, idClassFields);
    }
    // 별도의 Id Class 가 없다면 Entity 기준으로 단일 키 판단을 한다.
    final String idColumnName = entityIds.keySet().stream().findAny()
        .orElseThrow(() -> new RuntimeException("Not found any id column"));

    return new MapSqlParameterSource(idColumnName, id);
  }

  private Map<String, Field> getColumns(Class<?> clazz) {
    return Arrays.stream(clazz.getDeclaredFields())
        .filter(field -> field.getAnnotation(Column.class) != null)
        .peek(field -> field.setAccessible(true))
        .collect(
            Collectors.toUnmodifiableMap(
                field -> field.getAnnotation(Column.class).name(),
                Function.identity()
            )
        );
  }

  private boolean hasIdClass(Class<?> idClass) {
    return Long.class != idClass
        && Integer.class != idClass
        && String.class != idClass;
  }

  private Optional<T> find(final SimpleJdbcCall sp, final MapSqlParameterSource param) {
    final var resultList = findAll(sp, param);
    return resultList.stream().findAny();
  }

  private List<T> findAll(final SimpleJdbcCall sp, final MapSqlParameterSource param) {
    // null-safe
    var resultMap = sp.execute(param);
    var resultTable = resultMap.get(DEFAULT_RESULT_MAP_ID);

    return PaybackCollections.orEmpty((List<T>) resultTable);
  }

  public Optional<T> findById(ID id) {
    Assert.state(spFindById != null, "not exist findById procedure");
    return find(spFindById, convertToIdParameter(id));
  }

  protected Optional<T> findBy(String spName, MapSqlParameterSource param) {
    Assert.state(spFindAllByMap != null || !spFindAllByMap.isEmpty(), "not exist findAllByEtc procedures");

    var spFindAllByEtc = spFindAllByMap.getOrDefault(spName, null);
    Assert.state(spFindAllByEtc != null, "not exist findAllByEtc procedure named " + spName);

    return find(spFindAllByEtc, param);
  }

  protected List<T> findAllBy(String spName, MapSqlParameterSource param) {
    Assert.state(spFindAllByMap != null || !spFindAllByMap.isEmpty(), "not exist findAllByEtc procedures");

    var spFindAllByEtc = spFindAllByMap.getOrDefault(spName, null);
    Assert.state(spFindAllByEtc != null, "not exist findAllByEtc procedure named " + spName);

    return findAll(spFindAllByEtc, param);
  }

  // FIXME : save 메소드 일원화 필요
  protected ID saveAndGetId(MapSqlParameterSource param, String idColumn) {
    // fixme : Entity로 입력받아 처리할까..?, 복합키인 경우 outputParam은 어떻게 할까....?, ID 컬럼만 별도 필드에 저장?
    Assert.state(spSaveReturnsKey != null, "not exist saveReturnsKey procedure");

    // null-safe
    var resultMap = spSaveReturnsKey.execute(param);

    return (ID) resultMap.get(idColumn);
  }

  // FIXME : save 메소드 일원화 필요
  protected <R> R save(String spName, MapSqlParameterSource param, String returnCodeParamName) {
    Assert.state(spSaveMap != null || !spFindAllByMap.isEmpty(), "not exist save procedures");

    var spSave = spSaveMap.getOrDefault(spName, null);
    Assert.state(spSave != null, "not exist spSave procedure named " + spName);

    return find(spSave, param)
        .stream()
        .findFirst()
        .map(Map.class::cast)
        .map(item -> (R)item.get(returnCodeParamName))
        .orElse(null);
  }
}
