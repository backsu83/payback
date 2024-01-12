package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb;

import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventRequestEntity;
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
      procedureName = SmileCashEventRequestEntity.SAVE,
      parameters = {
          @SaturnProcedureParameter(name = "COMMN_TYPE", sqlType = Types.VARCHAR, scale = 4),
          @SaturnProcedureParameter(name = "REQ_MONEY", sqlType = Types.DECIMAL),
          @SaturnProcedureParameter(name = "REQ_OUTPUT_IMPB_MONEY", sqlType = Types.DECIMAL),
          @SaturnProcedureParameter(name = "CASH_BALANCE_TYPE", sqlType = Types.CHAR, scale = 2),
          @SaturnProcedureParameter(name = "CUST_NO", sqlType = Types.VARCHAR, scale = 10),
          @SaturnProcedureParameter(name = "EXPIRE_DT", sqlType = Types.TIMESTAMP),
          @SaturnProcedureParameter(name = "REF_NO", sqlType = Types.BIGINT),
          @SaturnProcedureParameter(name = "ERS_NO", sqlType = Types.INTEGER),
          @SaturnProcedureParameter(name = "REG_ID", sqlType = Types.BIGINT),
          @SaturnProcedureParameter(name = "EID",  sqlType = Types.BIGINT),
          @SaturnProcedureParameter(name = "COMMENTS",  sqlType = Types.VARCHAR, scale = 60)
      },
      throwEx = true
  )
  public Optional<SmileCashEventResultEntity> save(final SmileCashEventRequestEntity entity) {
    return Optional.empty();
  }

  @SaturnProcedure(
      procedureName = SmileCashEventRequestEntity.FIND,
      parameters = {
          @SaturnProcedureParameter(name = "CASH_BALANCE_TYPE", sqlType = Types.CHAR, scale = 2),
          @SaturnProcedureParameter(name = "CUST_NO", sqlType = Types.VARCHAR, scale = 10),
          @SaturnProcedureParameter(name = "REF_NO", sqlType = Types.BIGINT),
          @SaturnProcedureParameter(name = "ERS_NO", sqlType = Types.INTEGER)
      },
      throwEx = true
  )
  public Optional<SmileCashEventEntity> find(final SmileCashEventRequestEntity entity) {
    return Optional.empty();
  }
}
