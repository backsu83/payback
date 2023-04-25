package com.ebaykorea.payback;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.dto.ssgpoint.SaveSsgPointRequestDto;
import com.ebaykorea.payback.core.service.SsgPointService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
public class SsgPointTargetDummyCreteadTest {

  @Autowired
  SsgPointService ssgPointService;

  @Test
  void dummy() {

    //스케줄 시간 5분
    String startDateString = "2023-04-24 00:00:00";
    String endDateString = "2023-04-24 23:59:59";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime startDateTime = LocalDateTime.parse(startDateString, formatter);
    LocalDateTime endDateTime = LocalDateTime.parse(endDateString, formatter);

    var limit = 10;
    var count = 0;

    var startOrderNo = 8000000000L;
    var startPackNo = 800000000L;

    //스케줄 시간 (5분단위)
    for (LocalDateTime currentDateTime = startDateTime; currentDateTime.isBefore(endDateTime); currentDateTime = currentDateTime.plusMinutes(5)) {
      while (count < limit) {
        var request = SaveSsgPointRequestDto.builder()
            .orderNo(startOrderNo--)
            .packNo(startPackNo--)
            .payAmount(BigDecimal.valueOf(10000L))
            .saveAmount(BigDecimal.valueOf(10L))
            .buyerId("132871942") //SSGPOINT 카드가 있는 계정
            .siteType(OrderSiteType.Gmarket)
            .orderDate(currentDateTime.format(formatter))
            .scheduleDate(currentDateTime.format(formatter))
            .build();
        ssgPointService.earnPoint(request);
        count++;
      }
      count=0;
    }
  }
}
