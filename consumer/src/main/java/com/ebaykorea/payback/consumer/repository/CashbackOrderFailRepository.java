package com.ebaykorea.payback.consumer.repository;

import com.ebaykorea.payback.consumer.repository.entity.CashbackOrderFailEntity;
import com.ebaykorea.saturn.mssql.dbname.Gmkt;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedure;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedureParameter;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
@SaturnDataSource(name = Gmkt.TIGER_READ)
public class CashbackOrderFailRepository {

  @SaturnProcedure(procedureName = CashbackOrderFailEntity.SAVE,
      parameters = {
          @SaturnProcedureParameter(name = "ORDER_KEY", sqlType = Types.VARCHAR, scale = 30),
          @SaturnProcedureParameter(name = "TX_KEY", sqlType = Types.VARCHAR, scale = 30),
          @SaturnProcedureParameter(name = "RSP_CD", sqlType = Types.BIGINT),
      }
  )
  public void save(String orderKey, String txKey, Long rspCd) {
  }
}
