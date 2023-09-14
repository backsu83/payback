package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb;

import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventResultEntity;
import com.ebaykorea.saturn.mssql.dbname.Gmkt;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedure;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedureParameter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Optional;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.GMARKET_TENANT;

@Profile(GMARKET_TENANT)
@Repository
@SaturnDataSource(name = Gmkt.TIGER_READ)
public class SmileCashEventEntityRepository {
  @SaturnProcedure(
      procedureName = SmileCashEventResultEntity.SAVE,
      parameters = {
          @SaturnProcedureParameter(name = "PACK_NO", sqlType = Types.BIGINT),
          @SaturnProcedureParameter(name = "CONTR_NO", sqlType = Types.BIGINT),
          @SaturnProcedureParameter(name = "COMMN_TYPE", sqlType = Types.VARCHAR, scale = 4),
          @SaturnProcedureParameter(name = "TRADE_CD", sqlType = Types.VARCHAR, scale = 3),
          @SaturnProcedureParameter(name = "SMILECASH_CD", sqlType = Types.CHAR, scale = 4),
          @SaturnProcedureParameter(name = "REQ_MONEY", sqlType = Types.DECIMAL),
          @SaturnProcedureParameter(name = "REQ_OUTPUT_ENABLE_MONEY", sqlType = Types.DECIMAL),
          @SaturnProcedureParameter(name = "REQ_OUTPUT_IMPB_MONEY", sqlType = Types.DECIMAL),
          @SaturnProcedureParameter(name = "CASH_BALANCE_TYPE", sqlType = Types.CHAR, scale = 2),
          @SaturnProcedureParameter(name = "CUST_NO", sqlType = Types.VARCHAR, scale = 10),
          @SaturnProcedureParameter(name = "COMMENTS", sqlType = Types.VARCHAR, scale = 60),
          @SaturnProcedureParameter(name = "EXPIRE_DT", sqlType = Types.TIMESTAMP),
          @SaturnProcedureParameter(name = "REF_NO", sqlType = Types.BIGINT),
          @SaturnProcedureParameter(name = "ERS_NO", sqlType = Types.INTEGER),
          @SaturnProcedureParameter(name = "REG_ID", sqlType = Types.BIGINT),

          @SaturnProcedureParameter(name = "RET_NO", sqlType = Types.INTEGER, out = true),
          @SaturnProcedureParameter(name = "RET_COMMENT", sqlType = Types.VARCHAR, scale = 200, out = true),
          @SaturnProcedureParameter(name = "RET_SMILEPAY_NO", sqlType = Types.BIGINT, out = true),
      },
      throwEx = true
  )
  public Optional<SmileCashEventResultEntity> save(final SmileCashEventEntity entity) {
    return Optional.empty();
  }
}
