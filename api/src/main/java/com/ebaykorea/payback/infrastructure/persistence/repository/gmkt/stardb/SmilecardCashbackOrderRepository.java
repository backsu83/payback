package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb;

import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmilecardCashbackOrderEntity;
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
public class SmilecardCashbackOrderRepository {

  @SaturnProcedure(
      procedureName = SmilecardCashbackOrderEntity.FIND_BY_ID,
      parameters = {
          @SaturnProcedureParameter(name = "PACK_NO", sqlType = Types.BIGINT)
      }
  )
  public Optional<SmilecardCashbackOrderEntity> findById(final Long packNo) {
    return Optional.empty();
  }

  @SaturnProcedure(
      procedureName = SmilecardCashbackOrderEntity.SAVE,
      parameters = {
          @SaturnProcedureParameter(name = "PACK_NO", sqlType = Types.BIGINT),
          @SaturnProcedureParameter(name = "SMILECARD_CASHBACK_AMOUNT", sqlType = Types.DECIMAL),
          @SaturnProcedureParameter(name = "SMILECARD_CASHBACK_APPLY_YN", sqlType = Types.VARCHAR, scale = 1),
          @SaturnProcedureParameter(name = "REG_ID", sqlType = Types.VARCHAR, scale = 10),
          @SaturnProcedureParameter(name = "REG_DT", sqlType = Types.TIMESTAMP),

          @SaturnProcedureParameter(name = "CHG_ID", sqlType = Types.VARCHAR, scale = 10),
          @SaturnProcedureParameter(name = "T2T3_CASHBACK_AMOUNT", sqlType = Types.DECIMAL),
          @SaturnProcedureParameter(name = "T2T3_CASHBACK_APPLY_YN", sqlType = Types.VARCHAR, scale = 1),
          @SaturnProcedureParameter(name = "ITEM_TYPE", sqlType = Types.VARCHAR, scale = 2)
      },
      throwEx = true //입력 실패시 exception 발생
  )
  public void save(final SmilecardCashbackOrderEntity entity) {

  }
}
