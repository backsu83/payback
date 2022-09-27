package com.ebaykorea.payback.port.persistence.repository.stardb.impl;

import com.ebaykorea.payback.adapter.persistence.repository.SqlServerRepository;
import com.ebaykorea.payback.core.repository.CashbackOrderRepository;
import com.ebaykorea.payback.port.persistence.repository.stardb.entity.CashbackOrderEntity;
import com.ebaykorea.payback.port.persistence.repository.stardb.entity.CashbackOrderEntityId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.Map;

@Repository
public class CashbackOrderRepositoryImpl extends SqlServerRepository<CashbackOrderEntity, CashbackOrderEntityId>
    implements CashbackOrderRepository {
  public CashbackOrderRepositoryImpl(@Qualifier("tigerDataSource") final DataSource dataSource) {
    super(dataSource);
  }

  @Override
  protected SimpleJdbcCall getFindByIdProcedure() {
    return new SimpleJdbcCall(this.jdbcTemplate)
        .withoutProcedureColumnMetaDataAccess()
        .withCatalogName("stardb")
        .withSchemaName("dbo")
        .withProcedureName(CashbackOrderEntity.FIND_BY_ID)
        .declareParameters(
            new SqlParameter("BUY_ORDER_NO", Types.BIGINT),
            new SqlParameter("CASHBACK_TYPE", Types.CHAR, 1),
            new SqlParameter("TRADE_CD", Types.VARCHAR, 3)
        )
        .returningResultSet(
            DEFAULT_RESULT_MAP_ID,
            this.propertyRowMapper
        );
  }

  @Override
  protected Map<String, SimpleJdbcCall> getSaveProcedures() {
    return Map.ofEntries(
        Map.entry(
            CashbackOrderEntity.SAVE,
            new SimpleJdbcCall(this.jdbcTemplate)
                .withoutProcedureColumnMetaDataAccess()
                .withCatalogName("stardb")
                .withSchemaName("dbo")
                .withProcedureName(CashbackOrderEntity.SAVE)
                .declareParameters(
                    new SqlParameter("BUY_ORDER_NO", Types.BIGINT),
                    new SqlParameter("CASHBACK_TYPE", Types.CHAR, 1),
                    new SqlParameter("TRADE_CD", Types.VARCHAR, 3),
                    new SqlParameter("CASHBACK_MONEY", Types.DECIMAL),
                    new SqlParameter("CASHBACK_BASIS_MONEY", Types.DECIMAL),
                    new SqlParameter("GD_NO", Types.VARCHAR, 10),
                    new SqlParameter("PACK_NO", Types.BIGINT),
                    new SqlParameter("CUST_NO", Types.VARCHAR, 10),
                    new SqlParameter("USER_KEY", Types.VARCHAR, 200),
                    new SqlParameter("TRADE_STATUS", Types.CHAR, 2),
                    new SqlParameter("REQ_CANCEL_YN", Types.CHAR, 1),
                    new SqlParameter("USE_ENABLE_DT", Types.TIMESTAMP),
                    new SqlParameter("SITE_TYPE", Types.CHAR, 1),
                    new SqlParameter("CASHBACK_REQ_SEQ", Types.BIGINT),
                    new SqlParameter("REG_ID", Types.VARCHAR, 10),
                    new SqlParameter("REG_DT", Types.TIMESTAMP),
                    new SqlParameter("CHG_ID", Types.VARCHAR, 10),
                    new SqlParameter("SMILE_CLUB_YN", Types.CHAR, 1),
                    new SqlParameter("SHOP_TYPE", Types.CHAR, 2)
                )
        )
    );
  }
}
