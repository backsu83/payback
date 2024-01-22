package com.ebaykorea.payback.scheduler.repository.maindb2ex;

import static com.ebaykorea.payback.scheduler.model.constant.TenantCode.AUCTION_TENANT;
import static java.util.Collections.emptyList;

import com.ebaykorea.payback.scheduler.repository.maindb2ex.entity.SmileCashSaveApprovalEntity;
import com.ebaykorea.payback.scheduler.repository.maindb2ex.entity.SmileCashSaveQueueEntity;
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
      },
      throwEx = true
  )
  public List<SmileCashSaveQueueEntity> findTargets(final int maxRows, final int saveStatus, final int maxRetryCount) {
    return emptyList();
  }

  @SaturnProcedure(
      procedureName = SmileCashSaveQueueEntity.UPDATE,
      parameters = {
          @SaturnProcedureParameter(name = "SEQNO", sqlType = Types.BIGINT),
          @SaturnProcedureParameter(name = "SAVE_STATUS", sqlType = Types.TINYINT),
          @SaturnProcedureParameter(name = "RETRY_CNT", sqlType = Types.INTEGER),
          @SaturnProcedureParameter(name = "INS_OPRT", sqlType = Types.VARCHAR, scale = 30)
      },
      throwEx = true
  )
  public void update(final long seqno, final int saveStatus, final int retryCnt, final String insOprt) {
  }

  @SaturnProcedure(
      procedureName = SmileCashSaveApprovalEntity.INSERT,
      parameters = {
          @SaturnProcedureParameter(name = "IAC_TXID", sqlType = Types.BIGINT),
          @SaturnProcedureParameter(name = "SMILECASH_TXID", sqlType = Types.CHAR, scale = 20),
          @SaturnProcedureParameter(name = "SMILE_MEMB_KEY", sqlType = Types.VARCHAR, scale = 200),
          @SaturnProcedureParameter(name = "TXN_TYPE", sqlType = Types.TINYINT),
          @SaturnProcedureParameter(name = "SMILECASH_TXN_DATE", sqlType = Types.DATE),
          @SaturnProcedureParameter(name = "SMILECASH_TYPE", sqlType = Types.TINYINT),
          @SaturnProcedureParameter(name = "APPR_AMNT", sqlType = Types.DECIMAL),
          @SaturnProcedureParameter(name = "SMILECASH_EXPIRE_DATE", sqlType = Types.DATE),
          @SaturnProcedureParameter(name = "DIFF_PROC_BASE_DATE", sqlType = Types.DATE),
          @SaturnProcedureParameter(name = "DIFF_PROC_IS", sqlType = Types.BIT),
          @SaturnProcedureParameter(name = "IAC_REASON_CODE", sqlType = Types.CHAR, scale = 5),
          @SaturnProcedureParameter(name = "IAC_REASON_COMMENT", sqlType = Types.VARCHAR, scale = 100),
          @SaturnProcedureParameter(name = "IAC_REASON_ADD", sqlType = Types.VARCHAR, scale = 100),
          @SaturnProcedureParameter(name = "IAC_BIZ_TYPE", sqlType = Types.TINYINT),
          @SaturnProcedureParameter(name = "IAC_BIZ_KEY", sqlType = Types.VARCHAR, scale = 50),
          @SaturnProcedureParameter(name = "INS_OPRT", sqlType = Types.VARCHAR, scale = 30),
      },
      throwEx = true
  )
  public void saveApproval(final SmileCashSaveApprovalEntity entity) {
  }
}
