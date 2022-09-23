package com.ebaykorea.payback.adapter.persistence.repository.tiger;

import com.ebaykorea.payback.core.domain.entity.saturn.TestServerInfo;
import com.ebaykorea.saturn.starter.annotation.SaturnDataSource;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedure;
import com.ebaykorea.saturn.starter.annotation.SaturnProcedureParameter;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
@SaturnDataSource(name = "TIGER")
public class TestServerInfoRepository {

  @SaturnProcedure(
      procedureName = "dbo.sp_server_info",
      parameters = {
          @SaturnProcedureParameter(name = "attribute_id", sqlType = Types.INTEGER)
      }
  )
  public TestServerInfo getServerInfo(int attributeId) {
    return null;
  }
}

