package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity;

import static com.ebaykorea.payback.util.PaybackInstants.now;

import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.SsgPointTargetRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SsgPointTargetEntityTest {

  @Autowired
  SsgPointTargetRepository ssgPointTargetRepository;

  @Test
  void saveTest() {
    SsgPointTargetEntity entity = SsgPointTargetEntity.builder()
        .orderNo(13092892548L)
        .buyerId("123456789")
        .siteType("G")
        .tradeType("S")
        .receiptNo("2302031607000004")
        .orgReceiptNo("2302031607000004")
        .payAmount(BigDecimal.valueOf(1000))
        .saveAmount(BigDecimal.valueOf(1000))
        .pointStatus("WW")
        .cancelYn("N")
        .pointToken("A2302031785445298")
        .orderDate(now())
        .scheduleDate(now())
        .accountDate("20230224")
        .responseCode("API0000")
        .trcNo("20230203162453280693")
        .tradeNo("2302030002")
        .packNo(5085547185L)
        .build();

    ssgPointTargetRepository.save(entity);
  }

}