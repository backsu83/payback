package com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex;

import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashReasonCodeEntity;
import com.ebaykorea.saturn.mssql.dbname.Gmkt;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedure;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedureParameter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Optional;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.AUCTION_TENANT;

@Profile(AUCTION_TENANT)
@Repository
@SaturnDataSource(name = Gmkt.MAINDB2_READ)
public class SmileCashReasonCodeRepository {
  @SaturnProcedure(
      procedureName = SmileCashReasonCodeEntity.FIND,
      parameters = {
          @SaturnProcedureParameter(name = "IAC_REASON_CODE", sqlType = Types.CHAR, scale = 5)
      },
      throwEx = true
  )
  public Optional<SmileCashReasonCodeEntity> findById(final String iacReasonCode) {
    return Optional.empty();
  }
}
