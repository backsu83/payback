package com.ebaykorea.payback;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.dto.ssgpoint.SaveSsgPointRequestDto;
import com.ebaykorea.payback.core.service.SsgPointService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
public class SsgPointTargetDummyCreteadTest {

  @Autowired
  SsgPointService ssgPointService;

  /**
   * 테스트 데이터 생성
   */
  @Test
  void ssgPointTargetDataDummy() {

    //스케줄 시간 5분
    String startDateString = "2023-04-20 00:00:00";
    String endDateString = "2023-04-20 23:59:59";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime startDateTime = LocalDateTime.parse(startDateString, formatter);
    LocalDateTime endDateTime = LocalDateTime.parse(endDateString, formatter);

    //구간당 생성되는 데이터 갯수
    var limit = 5000;
    var count = 0;

    var startOrderNo = 7999000000L;
    var startPackNo = 799900000L;

    //스레드 갯수 (20)
    ExecutorService executor = Executors.newFixedThreadPool(20);
    List<CompletableFuture> futures = Lists.newArrayList();

    //스케줄 시간 (5분단위)
    for (LocalDateTime currentDateTime = startDateTime; currentDateTime.isBefore(endDateTime); currentDateTime = currentDateTime.plusMinutes(1)) {
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
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
          ssgPointService.earnPoint(request);
          return null;
        }, executor);
        futures.add(future);
        count++;
      }
      CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
          .thenApply(s -> {
            List<Object> result = futures.stream()
                .filter(f-> Objects.nonNull(f))
                .map(pageContentFuture -> pageContentFuture.join())
                .collect(Collectors.toList());
            return result;
          }).join();
      count=0;
    }
  }
}
