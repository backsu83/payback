package com.ebaykorea.payback.infrastructure.persistence.repository;

import java.util.Optional;

public interface PaybackSqlRepository<ID, T> {
  Optional<T> findById(ID id);
  void save(T t);
}
