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
}
