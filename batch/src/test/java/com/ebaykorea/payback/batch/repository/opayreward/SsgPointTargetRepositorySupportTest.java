package com.ebaykorea.payback.batch.repository.opayreward;


import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
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
    var result = ssgPointTargetRepositorySupport.findByStatusReadyBy();
    System.out.println(GsonUtils.toJsonPretty(result));
  }

  @Test
  @Transactional
  void updateFailBy() {
    var result = ssgPointTargetRepositorySupport.updateFailBy(12345677889L , "G", "S");
    System.out.println(result);
  }
}