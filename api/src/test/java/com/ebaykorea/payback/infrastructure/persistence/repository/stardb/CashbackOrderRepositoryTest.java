package com.ebaykorea.payback.infrastructure.persistence.repository.stardb;

import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.entity.CashbackOrderEntity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CashbackOrderRepositoryTest {

  @Autowired
  CashbackOrderRepository cashbackOrderRepository;

  @Test
  @Transactional
  public void saveAndFindTest() {

    final var cashbackOrderEntity = buildCashbackOrderEntity();
    cashbackOrderRepository.save(cashbackOrderEntity);
    final var result = cashbackOrderRepository.findById(cashbackOrderEntity);

    assertNotEquals(Optional.empty(), result);
    assertEquals(cashbackOrderEntity.getBuyOrderNo(), result.get().getBuyOrderNo());
  }

  private CashbackOrderEntity buildCashbackOrderEntity() {
    final var nowDate = Timestamp.from(Instant.now());
    return CashbackOrderEntity.builder()
        .buyOrderNo(1L)
        .cashbackType("I")
        .tradeCd("SV")
        .cashbackMoney(BigDecimal.TEN)
        .cashbackBasisMoney(BigDecimal.TEN)
        .gdNo("gdNo")
        .packNo(1L)
        .custNo("custNo")
        .userKey("userKey")
        .tradeStatus("T")
        .useEnableDt(nowDate)
        .siteType("G")
        .smileClubYn("N")
        .regId("regId")
        .regDt(nowDate)
        .shopType(null)
        .build();
  }
}