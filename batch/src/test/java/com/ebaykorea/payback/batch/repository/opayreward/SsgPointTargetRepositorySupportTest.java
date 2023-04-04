package com.ebaykorea.payback.batch.repository.opayreward;


import com.ebaykorea.payback.batch.domain.constant.PointStatusType;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntity;
import com.ebaykorea.payback.batch.util.support.GsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class SsgPointTargetRepositorySupportTest {

  @Autowired
  SsgPointTargetRepositorySupport ssgPointTargetRepositorySupport;

  @Test
  void findByStatusReadyBy() {
    var result = ssgPointTargetRepositorySupport.findStatusTest();
    for (SsgPointTargetEntity entity : result) {
      System.out.println(GsonUtils.toJsonPretty(entity.getOrderNo() + "|" + entity.getPointStatus()));
    }
  }

  @Test
  @Transactional
  void updateFailBy() {
    var result = ssgPointTargetRepositorySupport.updatePrcoesserFailBy(12345677889L , "G", "S",
        PointStatusType.Ready.getCode());
    System.out.println(result);
  }
}