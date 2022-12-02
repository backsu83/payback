package com.ebaykorea.payback.scheduler.infrastructure.repository;

import com.ebaykorea.payback.scheduler.infrastructure.repository.entity.CashbackOrderBatchEntity;
import com.ebaykorea.saturn.mssql.dbname.Gmkt;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedure;
import com.google.common.collect.Lists;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@SaturnDataSource(name = Gmkt.TIGER_READ)
public class CashbackOrderBatchRepository {

  @SaturnProcedure(procedureName = CashbackOrderBatchEntity.FIND_BY_KEYS)
  public List<CashbackOrderBatchEntity> findOrderkeyAndTxkey() {
    return Lists.newArrayList();
  }

}
