package com.ebaykorea.payback;

import com.ebaykorea.payback.api.CashbackController;
import com.ebaykorea.payback.api.SmilePointController;
import com.ebaykorea.payback.api.SsgPointController;
import com.ebaykorea.payback.core.dto.cashback.CashbackResponseDto;
import com.ebaykorea.payback.core.dto.cashback.SaveCashbackRequestDto;
import com.ebaykorea.payback.api.dto.smilepoint.SaveSmilePointRequestDto;
import com.ebaykorea.payback.api.dto.smilepoint.SmilePointHistoryRequestDto;
import com.ebaykorea.payback.api.dto.smilepoint.SmilePointStatusContrNoRequestDto;
import com.ebaykorea.payback.api.dto.smilepoint.SmilePointStatusSmilepayRequestDto;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.customer.SmilePointTradeRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.*;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.CashbackOrderEntity;
import com.ebaykorea.payback.infrastructure.query.data.SavedCashbackQueryResult;
import com.ebaykorea.payback.infrastructure.query.data.SmilePointTradeQueryResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.specto.hoverfly.junit5.HoverflyExtension;
import io.specto.hoverfly.junit5.api.HoverflyConfig;
import io.specto.hoverfly.junit5.api.HoverflySimulate;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.ebaykorea.payback.core.domain.constant.ResponseMessageType.CASHBACK_CREATED;
import static com.ebaykorea.payback.support.TestConstants.*;
import static io.specto.hoverfly.junit5.api.HoverflySimulate.SourceType.FILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Disabled //TODO: jenkins에서 의도대로 동작되지 않아 componentTest 임시제거
public class PaybackComponentTest {
  @Nested
  @TestInstance(TestInstance.Lifecycle.PER_CLASS)
  @HoverflySimulate(
      config = @HoverflyConfig(
          proxyLocalHost = true
      ),
      source = @HoverflySimulate.Source(value = HOVERFLY_ROOT + HOVERFLY_FILE, type = FILE), //외부 api 결과를 resource/hoverfly/hoverfly-stubs에 정의 되어 있는 값들을 리턴하도록
      enableAutoCapture = true)
  @ExtendWith(HoverflyExtension.class)
  @EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
  @SpringBootTest(properties = {"payback.dcm.access.enable=false"})
  class Cashback {
    @Autowired
    CashbackController cashbackController;
    @Autowired
    SsgPointController ssgPointController;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CashbackOrderDetailRepository cashbackOrderDetailRepository;
    @MockBean
    CashbackOrderMemberRepository cashbackOrderMemberRepository;
    @MockBean
    CashbackOrderPolicyRepository cashbackOrderPolicyRepository;
    @MockBean
    CashbackOrderRepository cashbackOrderRepository;
    @MockBean
    SmilecardCashbackOrderRepository smilecardCashbackOrderRepository;
    @MockBean
    SmilecardT2T3CashbackRepository smilecardT2T3CashbackRepository;

    @Value("classpath:" + CASHBACK_QUERY_FILE)
    Resource resourceFile;

    private static final String txKey = "txKey";
    private static final String orderKey = "orderKey";


    @Test
    @DisplayName("캐시백 적립이 성공한다")
    void saveCashback() {

      doNothing().when(cashbackOrderDetailRepository).save(any());
      doNothing().when(cashbackOrderMemberRepository).save(any());
      doNothing().when(cashbackOrderPolicyRepository).save(any());
      doNothing().when(cashbackOrderRepository).save(any());
      doNothing().when(smilecardCashbackOrderRepository).save(any());
      doNothing().when(smilecardT2T3CashbackRepository).save(any());

      final var result = cashbackController.saveCashbacks(new SaveCashbackRequestDto(txKey, orderKey));

      assertEquals(CASHBACK_CREATED.name(), result.getMessage());
      assertEquals(0, result.getCode());
      assertEquals(CashbackResponseDto.of(txKey, orderKey), result.getData());
    }

    @Test
    @DisplayName("캐시백 조회가 성공한다")
    void getCashbacks() {
      when(cashbackOrderRepository.findByPackNo(anyLong()))
          .thenReturn(List.of(
              CashbackOrderEntity.builder()
                  .cashbackType("I")
                  .amount(BigDecimal.valueOf(2295L))
                  .itemNo("1100439676")
                  .buyerNo("132870993")
                  .tradeStatus("30")
                  .smileClubYn("Y")
                  .useEnableDt(Timestamp.valueOf("2023-01-04 00:00:00.0"))
                  .build()));

      final var result = cashbackController.getSavedCashbacks(null, txKey, orderKey);
      final var expected = getExpectResult();

      assertEquals(expected, result);
    }

    private SavedCashbackQueryResult getExpectResult() {
      try {
        return objectMapper.readValue(new ClassPathResource(CASHBACK_QUERY_FILE).getFile(), SavedCashbackQueryResult.class);
      } catch (Exception ex) {
        return null;
      }
    }

    private final Long packNo = 5085547185L;
    private static final String siteType = "G";
    @Test
    @DisplayName("신세계 포인트 조회")
    void getSsgPoints() {
      final var result = ssgPointController.getSsgPoints(packNo, siteType);
    }
  }


  private static final String PATTERN_FORMAT = "yyyy-MM-dd";
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
      .withZone(ZoneId.systemDefault());
  @Nested
  @Disabled("현재 정상적으로 통과를 하나, 내부 로직이 실제 데이터와 많은 의존성이 있어(ex: 실제 주문 데이터, 유저키 존재 여부 등) 추후 테스트가 정상적으로 동작 되지 않을 수 있음")
  @SpringBootTest
  class SmilePoint {
    @Autowired
    SmilePointController smilePointController;
    @Autowired
    SmilePointTradeRepository smilePointTradeRepository;

    @Test
    @Transactional //Test 실행 후 내부에서 입력된 데이터는 롤백 됨
    public void smilePointTest() {
      final var request = createRequest();
      final var formattedDate = getFormattedDate();
      final var smilePayNo = (long)smilePointController.saveSmilePointRequest(request).getData();
      final var result1 = (SmilePointTradeQueryResult)smilePointController.statusBySmilePayNo(SmilePointStatusSmilepayRequestDto.builder().smilePayNo(smilePayNo).build()).getData();
      final var result2 = (List<SmilePointTradeQueryResult>)smilePointController.statusByContrNo(SmilePointStatusContrNoRequestDto.builder().buyerNo(request.getBuyerNo()).contrNo(request.getContrNo()).build()).getData();
      final var result3 = (List<SmilePointTradeQueryResult>)smilePointController.history(SmilePointHistoryRequestDto.builder().buyerNo(request.getBuyerNo()).startDate(formattedDate).endDate(formattedDate).build()).getData();

      assertEquals(smilePayNo, result1.getSmilePayNo());
      assertResult(request, result1);
      result2.forEach(r -> assertResult(request, r));
      result3.forEach(r -> assertResult(request, r));
    }

    private SaveSmilePointRequestDto createRequest() {
      return SaveSmilePointRequestDto.builder()
          .buyerNo("115038223")
          .pointAmount(100)
          .reasonCode(71)
          .comment("componentTest")
          .contrNo(2542086899L)
          .easNo(0)
          .eventId(0)
          .winNo(0)
          .sellerId("sellerId")
          .build();
    }

    private String getFormattedDate() {
      return FORMATTER.format(Instant.now());
    }

    private void assertResult(final SaveSmilePointRequestDto request, final SmilePointTradeQueryResult result) {
      assertEquals(request.getBuyerNo(), result.getBuyerNo());
      assertEquals(request.getPointAmount(), result.getPoint());
      assertEquals(request.getReasonCode(), result.getReasonCode());
      assertEquals(request.getComment(), result.getComment());
      assertEquals(request.getContrNo(), result.getContrNo());
    }
  }
}
