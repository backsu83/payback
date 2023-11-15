package com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex;

import com.ebaykorea.saturn.mssql.dbname.Gmkt;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedure;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedureParameter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Types;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.AUCTION_TENANT;

@Profile(AUCTION_TENANT)
@Repository
@SaturnDataSource(name = Gmkt.MAINDB2_READ)
public class SmileCashTransactionRepository {
  @SaturnProcedure(
      procedureName = "maindb2ex.dbo.UPIAC_Escrow_SmileCashTransaction_InsertByNoValue",
      parameters = {
          @SaturnProcedureParameter(name = "INS_OPRT", sqlType = Types.VARCHAR, scale = 30)
      },
      throwEx = true
  )
  public long getIacTxId(final String insOprt) {
    return 0L;
  }
}
