package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.port.persistence.repository.stardb.entity.CashbackOrderEntity;
import com.ebaykorea.payback.port.persistence.repository.stardb.entity.CashbackOrderEntityId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//TODO: 임시 코드 제거 예정
@Repository
public interface CashbackOrderRepository  {
    Optional<CashbackOrderEntity> findById(CashbackOrderEntityId id);
}
