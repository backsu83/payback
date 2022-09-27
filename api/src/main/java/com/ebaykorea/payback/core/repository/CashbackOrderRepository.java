package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.port.persistence.repository.stardb.entity.CashbackOrderEntity;
import com.ebaykorea.payback.port.persistence.repository.stardb.entity.CashbackOrderEntityId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CashbackOrderRepository  {
    Optional<CashbackOrderEntity> findById(CashbackOrderEntityId id);
}
