package com.ebaykorea.payback.infrastructure.persistence.repository;


import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointRequestKey;
import com.ebaykorea.payback.util.support.GsonUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
class SsgPointRepositoryImplTest {

  @Autowired
  SsgPointRepositoryImpl ssgPointRepository;

  @Test
  void findByKey() {
    var key = SsgPointRequestKey.builder()
        .buyerId("102773607")
        .orderNo(5408289517L)
        .pointTradeType(PointTradeType.Save)
        .siteType(OrderSiteType.Gmarket).build();
    var entity = ssgPointRepository.findByKey(key);
    System.out.println(GsonUtils.toJsonPretty(entity));
  }
}