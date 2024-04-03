package com.ebaykorea.payback.schedulercluster.repository.stardb;


import static com.ebaykorea.payback.schedulercluster.model.constant.TenantCode.GMARKET_TENANT;
import static java.util.Collections.emptyList;

import com.ebaykorea.payback.schedulercluster.repository.stardb.entity.SmileCashEventEntity;
import com.ebaykorea.saturn.mssql.dbname.Gmkt;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedure;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedureParameter;
import java.sql.Types;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile(GMARKET_TENANT)
@Repository
@SaturnDataSource(name = Gmkt.TIGER_READ)
public class SmileCashEventRepository {

  @SaturnProcedure(
      procedureName = SmileCashEventEntity.FIND_TARGETS,
      parameters = {
          @SaturnProcedureParameter(name = "MAX_ROWS", sqlType = Types.INTEGER),
          @SaturnProcedureParameter(name = "APPR_STATUS", sqlType = Types.TINYINT),
          @SaturnProcedureParameter(name = "MAX_RETRY_COUNT", sqlType = Types.INTEGER),
      },
      throwEx = true
  )
  public List<SmileCashEventEntity> findTargets(final int maxRows, final int apprStatus, final int maxRetryCount) {
    return emptyList();
  }

  @SaturnProcedure(
      procedureName = SmileCashEventEntity.UPDATE,
      parameters = {
          @SaturnProcedureParameter(name = "SMILEPAY_NO", sqlType = Types.BIGINT),
          @SaturnProcedureParameter(name = "APPR_STATUS", sqlType = Types.TINYINT),
          @SaturnProcedureParameter(name = "RETRY_CNT", sqlType = Types.INTEGER),
          @SaturnProcedureParameter(name = "INS_OPRT", sqlType = Types.VARCHAR, scale = 30)
      },
      throwEx = true
  )
  public void update(final long smilepayNo, final int saveStatus, final int retryCnt, final String insOprt) {
  }
}
