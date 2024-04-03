package com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.AUCTION_TENANT;

import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveApprovalEntity;
import com.ebaykorea.saturn.mssql.dbname.Gmkt;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedure;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedureParameter;
import java.sql.Types;
import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile(AUCTION_TENANT)
@Repository
@SaturnDataSource(name = Gmkt.MAINDB2_READ)
public class SmileCashSaveApprovalRepository {
  @SaturnProcedure(
      procedureName = SmileCashSaveApprovalEntity.FIND_BY_ID,
      parameters = {
          @SaturnProcedureParameter(name = "IAC_TXID", sqlType = Types.BIGINT)
      },
      throwEx = true
  )
  public Optional<SmileCashSaveApprovalEntity> findById(final long iacTxid) {
    return Optional.empty();
  }

  @SaturnProcedure(
      procedureName = SmileCashSaveApprovalEntity.INSERT,
      parameters = {
          @SaturnProcedureParameter(name = "IAC_TXID", sqlType = Types.BIGINT),
          @SaturnProcedureParameter(name = "SMILECASH_TXID", sqlType = Types.CHAR, scale = 20),
          @SaturnProcedureParameter(name = "SMILE_MEMB_KEY", sqlType = Types.VARCHAR, scale = 200),
          @SaturnProcedureParameter(name = "TXN_TYPE", sqlType = Types.TINYINT),
          @SaturnProcedureParameter(name = "SMILECASH_TXN_DATE", sqlType = Types.TIMESTAMP),
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
  public void save(final SmileCashSaveApprovalEntity entity) {
  }
}
