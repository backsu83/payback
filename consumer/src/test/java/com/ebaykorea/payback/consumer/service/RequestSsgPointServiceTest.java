package com.ebaykorea.payback.consumer.service;


import com.ebaykorea.payback.consumer.repository.opayreward.CancelConsumerFailRepository;
import com.ebaykorea.payback.consumer.repository.opayreward.entity.CancelConsumerFailEntity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
class RequestSsgPointServiceTest {

  @Autowired
  CancelConsumerFailRepository repository;

  @Test
  void saveError() {
    var entity = CancelConsumerFailEntity.builder()
        .orderNo(23456789L)
        .packNo(123456789L)
        .siteType("G")
        .responseCode("-1")
        .responseMessage("responseMessage")
        .status("FAIL")
        .tryCnt(0L)
        .build();
    repository.save(entity);
  }
}