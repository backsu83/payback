package com.ebaykorea.payback.batch.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import com.ebaykorea.payback.batch.domain.constant.PointStatusType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Disabled
@SpringBootTest
class SsgPointBatchServiceTest {

  @Autowired
  SsgPointBatchService ssgPointBatchService;

  @Test
  void updateProcesserFail() {
    var result = ssgPointBatchService.updateProcesserFail(5408227299L ,
        "G",
        "S",
        PointStatusType.Fail.getCode());
    assertEquals(result, 1L);
  }

  @Test
  void name() {
    var result = ssgPointBatchService.getSsgAuthToken(
        "49E615F309BC23C5CA7E4603E2036977",
        "E320844B8E294F3E8D69395737C8B194",
        OrderSiteType.Gmarket);
    assertEquals(result, 1L);
  }
}

