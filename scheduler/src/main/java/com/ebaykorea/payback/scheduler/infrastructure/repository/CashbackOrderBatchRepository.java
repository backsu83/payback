package com.ebaykorea.payback.scheduler.infrastructure.repository;

import com.ebaykorea.payback.scheduler.infrastructure.repository.entity.CashbackOrderBatchEntity;
import com.ebaykorea.saturn.mssql.dbname.Gmkt;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedure;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedureParameter;
import com.google.common.collect.Lists;
import java.sql.Types;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@SaturnDataSource(name = Gmkt.TIGER_READ)
public class CashbackOrderBatchRepository {

  @SaturnProcedure(procedureName = CashbackOrderBatchEntity.FIND_BY_KEYS)
  public List<CashbackOrderBatchEntity> findNoCompleted() {
    return Lists.newArrayList();
  }

  @SaturnProcedure(procedureName = CashbackOrderBatchEntity.UPDATE_STATUS,
      parameters = {
          @SaturnProcedureParameter(name = "ORDER_KEY", sqlType = Types.VARCHAR, scale = 30),
          @SaturnProcedureParameter(name = "TX_KEY", sqlType = Types.VARCHAR, scale = 30),
          @SaturnProcedureParameter(name = "STATUS", sqlType = Types.VARCHAR, scale = 10),
          @SaturnProcedureParameter(name = "TRY_CNT", sqlType = Types.BIGINT),
          @SaturnProcedureParameter(name = "UPD_OPRT", sqlType = Types.VARCHAR, scale = 50)
      }
  )
  public void updateSatus(String orderKey, String txKey, String processType, Long retryCount , String updOprt) {
  }
}
