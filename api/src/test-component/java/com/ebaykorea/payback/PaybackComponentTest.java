package com.ebaykorea.payback;

import com.ebaykorea.payback.api.CashbackController;
import com.ebaykorea.payback.api.dto.SaveCashbackRequestDto;
import com.ebaykorea.payback.api.test.PaybackMvc;
import com.ebaykorea.saturn.datasource.EnableSaturnDataSource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.specto.hoverfly.junit5.HoverflyExtension;
import io.specto.hoverfly.junit5.api.HoverflyConfig;
import io.specto.hoverfly.junit5.api.HoverflySimulate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import static com.ebaykorea.payback.support.TestConstants.HOVERFLY_FILE;
import static com.ebaykorea.payback.support.TestConstants.HOVERFLY_ROOT;
import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.HttpBodyConverter.json;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
import static io.specto.hoverfly.junit5.api.HoverflySimulate.SourceType.FILE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@HoverflySimulate(
    config = @HoverflyConfig(
        proxyLocalHost = true
        //simulationPreprocessor = SimulationPreprocessor.class
    ),
    source = @HoverflySimulate.Source(value = HOVERFLY_ROOT + HOVERFLY_FILE, type = FILE),
    enableAutoCapture = true)
@ExtendWith(HoverflyExtension.class)
@SpringBootTest
//@EnableSaturnDataSource
public class PaybackComponentTest {

  @Autowired
  PaybackMvc paybackMvc;
  @Autowired
  CashbackController cashbackController;
  @Autowired
  ObjectMapper objectMapper;

  private String getPaybackRequest() throws JsonProcessingException {
    final var txKey = "txKey";
    final var orderKey = "orderKey";
    return objectMapper.writeValueAsString(new SaveCashbackRequestDto(txKey, orderKey));
  }

  @Test
  @Transactional
  @DisplayName("캐시백 적립이 성공한다")
  void saveCashback() throws Exception {

    final var result = paybackMvc.saveCashback(getPaybackRequest());
    //final var result = cashbackController.saveCashbacks(new SaveCashbackRequestDto("txKey", "orderKey"));

    assertEquals(result.getMessage(), HttpStatus.CREATED.name());
  }
}
