package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.GMARKET_TENANT;

import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventRequestEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventResultEntity;
import com.ebaykorea.saturn.mssql.dbname.Gmkt;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedure;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedureParameter;
import java.sql.Types;
import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

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
          @SaturnProcedureParameter(name = "COMMENTS",  sqlType = Types.VARCHAR, scale = 60),
          @SaturnProcedureParameter(name = "CONTR_NO",  sqlType = Types.BIGINT)
      },
      throwEx = true
  )
  public Optional<SmileCashEventResultEntity> save(final SmileCashEventRequestEntity entity) {
    return Optional.empty();
  }

  @SaturnProcedure(
      procedureName = SmileCashEventEntity.FIND,
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

  @SaturnProcedure(
      procedureName = SmileCashEventEntity.FIND_BY_SMILEPAY_NO,
      parameters = {
          @SaturnProcedureParameter(name = "SMILEPAY_NO", sqlType = Types.BIGINT),
      },
      throwEx = true
  )
  public Optional<SmileCashEventEntity> findBySmilePayNo(final long smilePayNo) {
    return Optional.empty();
  }

  @SaturnProcedure(
      procedureName = SmileCashEventEntity.UPDATE,
      parameters = {
          @SaturnProcedureParameter(name = "SMILEPAY_NO", sqlType = Types.BIGINT),
          @SaturnProcedureParameter(name = "CERT_APPR_ID", sqlType = Types.CHAR, scale = 20),
          @SaturnProcedureParameter(name = "RESULT_OUTPUT_ENABLE_MONEY", sqlType = Types.DECIMAL),
          @SaturnProcedureParameter(name = "RESULT_OUTPUT_IMPB_MONEY", sqlType = Types.DECIMAL),
          @SaturnProcedureParameter(name = "RESULT_MINUS_MONEY", sqlType = Types.DECIMAL),
          @SaturnProcedureParameter(name = "APPR_DT", sqlType = Types.TIMESTAMP),
          @SaturnProcedureParameter(name = "RET_EXPIRE_DT", sqlType = Types.TIMESTAMP),
          @SaturnProcedureParameter(name = "APPR_STATUS", sqlType = Types.TINYINT),
          @SaturnProcedureParameter(name = "RET_CD", sqlType = Types.CHAR, scale = 4),
          @SaturnProcedureParameter(name = "REG_ID", sqlType = Types.VARCHAR, scale = 10)
      },
      throwEx = true
  )
  public void update(final SmileCashEventEntity entity) {

  }
}
