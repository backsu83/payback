package com.ebaykorea.payback.scheduler.repository.stardb;

import com.ebaykorea.payback.scheduler.repository.stardb.entity.CashbackOrderBatchEntity;
import com.ebaykorea.saturn.mssql.dbname.Gmkt;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedure;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedureParameter;
import java.sql.Types;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import static com.ebaykorea.payback.scheduler.domain.constant.TenantCode.GMARKET_TENANT;

@Profile(GMARKET_TENANT)
@Repository
@SaturnDataSource(name = Gmkt.TIGER_READ)
public class CashbackOrderBatchRepository {

  @SaturnProcedure(procedureName = CashbackOrderBatchEntity.FIND_BY_KEYS,
      parameters = {
          @SaturnProcedureParameter(name = "MAX_TRY_CNT", sqlType = Types.BIGINT)
      })
  public List<CashbackOrderBatchEntity> findNoCompleted(final Long maxTryCnt) {
    return Collections.emptyList();
  }

  @SaturnProcedure(procedureName = CashbackOrderBatchEntity.UPDATE_STATUS,
      parameters = {
          @SaturnProcedureParameter(name = "ORDER_KEY", sqlType = Types.VARCHAR, scale = 30),
          @SaturnProcedureParameter(name = "TX_KEY", sqlType = Types.VARCHAR, scale = 30),
          @SaturnProcedureParameter(name = "STATUS", sqlType = Types.VARCHAR, scale = 10),
          @SaturnProcedureParameter(name = "TRY_CNT", sqlType = Types.BIGINT),
          @SaturnProcedureParameter(name = "RSP_MSG", sqlType = Types.VARCHAR, scale = 200),
          @SaturnProcedureParameter(name = "UPD_OPRT", sqlType = Types.VARCHAR, scale = 50)
      }
  )
  public void updateStatus(String orderKey, String txKey, String processType, Long retryCount , String message ,String updOprt) {
  }
}
