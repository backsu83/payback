package com.ebaykorea.payback.batch.repository.opayreward;


import com.ebaykorea.payback.batch.util.support.GsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SsgPointTargetRepositorySupportTest {

  @Autowired
  SsgPointTargetRepositorySupport ssgPointTargetRepositorySupport;

  @Test
  void name() {
    var result = ssgPointTargetRepositorySupport.findByStatusReady2();
    System.out.println(GsonUtils.toJsonPretty(result));
  }
}