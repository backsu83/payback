package com.ebaykorea.payback.batch.repository.opayreward;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import com.ebaykorea.payback.batch.domain.constant.PointStatusType;
import com.ebaykorea.payback.batch.domain.constant.VerifyTradeType;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntity;
import com.ebaykorea.payback.batch.util.support.GsonUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Disabled
@SpringBootTest
class SsgPointTargetRepositorySupportTest {

  @Autowired
  SsgPointTargetRepositorySupport ssgPointTargetRepositorySupport;

  @Test
  @Transactional
  void updateFailBy() {
    var result = ssgPointTargetRepositorySupport.updatePrcoesserFailBy(
        12345677889L ,
        "G",
        "S",
        PointStatusType.Ready.getCode());
    assertEquals(result, 1L);
  }

    @Test
    void findSumCount() {
      var result = ssgPointTargetRepositorySupport.findSumCount(
              OrderSiteType.Gmarket,
              VerifyTradeType.Cancel
      );
      System.out.println(GsonUtils.toJsonPretty(result));
    }
}