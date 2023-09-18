package com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex;


import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveQueueEntity;
import com.ebaykorea.saturn.mssql.dbname.Gmkt;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedure;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedureParameter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.AUCTION_TENANT;
import static java.util.Collections.emptyList;

@Profile(AUCTION_TENANT)
@Repository
@SaturnDataSource(name = Gmkt.MAINDB2_READ)
public class SmileCashSaveQueueRepository {


  @SaturnProcedure(
      procedureName = SmileCashSaveQueueEntity.FIND_BY_BIZ_KEY,
      parameters = {
          @SaturnProcedureParameter(name = "IAC_BIZ_KEY", sqlType = Types.VARCHAR, scale = 50)
      },
      throwEx = true
  )
  public List<SmileCashSaveQueueEntity> findByBizKey(final String iacBizKey) {
    return emptyList();
  }

  @SaturnProcedure(
      procedureName = SmileCashSaveQueueEntity.SAVE,
      parameters = {
          @SaturnProcedureParameter(name = "IAC_TXID", sqlType = Types.BIGINT),
          @SaturnProcedureParameter(name = "IAC_MEMB_ID", sqlType = Types.VARCHAR, scale = 20),
          @SaturnProcedureParameter(name = "IAC_BIZ_TYPE", sqlType = Types.TINYINT),
          @SaturnProcedureParameter(name = "IAC_BIZ_KEY", sqlType = Types.VARCHAR, scale = 50),
          @SaturnProcedureParameter(name = "IAC_REASON_CODE", sqlType = Types.CHAR, scale = 5),
          @SaturnProcedureParameter(name = "IAC_REASON_COMMENT", sqlType = Types.VARCHAR, scale = 100),
          @SaturnProcedureParameter(name = "IAC_REASON_ADD", sqlType = Types.VARCHAR, scale = 100),
          @SaturnProcedureParameter(name = "SMILECASH_TYPE", sqlType = Types.TINYINT),
          @SaturnProcedureParameter(name = "SAVE_AMNT", sqlType = Types.DECIMAL),
          @SaturnProcedureParameter(name = "EXPIRE_DATE", sqlType = Types.DATE),
          @SaturnProcedureParameter(name = "INS_OPRT", sqlType = Types.VARCHAR, scale = 30),
      },
      throwEx = true
  )
  public void save(final SmileCashSaveQueueEntity entity) {
  }
}
