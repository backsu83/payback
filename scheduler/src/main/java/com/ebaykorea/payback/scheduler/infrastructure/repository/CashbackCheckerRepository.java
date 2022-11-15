package com.ebaykorea.payback.scheduler.infrastructure.repository;

import com.ebaykorea.payback.scheduler.infrastructure.entity.CashbackCheckerEntity;
import com.ebaykorea.saturn.mssql.dbname.Gmkt;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedure;
import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.stereotype.Repository;


@Repository
@SaturnDataSource(name = Gmkt.TIGER_READ)
public class CashbackCheckerRepository {

  @SaturnProcedure(procedureName = CashbackCheckerEntity.FIND_BY_KEYS)
  public List<CashbackCheckerEntity> findOrderkeyAndTxkey() {
    return Lists.newArrayList();
  }
}
