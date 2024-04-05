package com.ebaykorea.payback.schedulercluster.repository.maindb2ex;

import static com.ebaykorea.payback.schedulercluster.model.constant.TenantCode.AUCTION_TENANT;
import static java.util.Collections.emptyList;

import com.ebaykorea.payback.schedulercluster.repository.maindb2ex.entity.SmileCashSaveQueueEntity;
import com.ebaykorea.saturn.mssql.dbname.Iac;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedure;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedureParameter;
import java.sql.Types;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile(AUCTION_TENANT)
@Repository
@SaturnDataSource(name = Iac.MAINDB2_READ)
public class SmileCashSaveQueueRepository {

  @SaturnProcedure(
      procedureName = SmileCashSaveQueueEntity.FIND_TARGETS,
      parameters = {
          @SaturnProcedureParameter(name = "MAX_ROWS", sqlType = Types.INTEGER),
          @SaturnProcedureParameter(name = "SAVE_STATUS", sqlType = Types.TINYINT),
          @SaturnProcedureParameter(name = "MAX_RETRY_COUNT", sqlType = Types.INTEGER),
          @SaturnProcedureParameter(name = "MOD", sqlType = Types.INTEGER),
          @SaturnProcedureParameter(name = "MOD_COUNT", sqlType = Types.INTEGER)
      },
      throwEx = true
  )
  public List<SmileCashSaveQueueEntity> findTargets(final int maxRows, final int saveStatus, final int maxRetryCount, final int mod, final int modCount) {
    return emptyList();
  }

  @SaturnProcedure(
      procedureName = SmileCashSaveQueueEntity.UPDATE,
      parameters = {
          @SaturnProcedureParameter(name = "SEQNO", sqlType = Types.BIGINT),
          @SaturnProcedureParameter(name = "SAVE_STATUS", sqlType = Types.TINYINT),
          @SaturnProcedureParameter(name = "RETRY_CNT", sqlType = Types.INTEGER),
          @SaturnProcedureParameter(name = "INS_OPRT", sqlType = Types.VARCHAR, scale = 10),
          @SaturnProcedureParameter(name = "USER_KEY", sqlType = Types.VARCHAR, scale = 200)
      },
      throwEx = true
  )
  public void update(final long seqno, final int saveStatus, final int retryCnt, final String insOprt, final String userKey) {
  }

}
