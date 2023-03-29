package com.ebaykorea.payback.batch.service;

import static org.junit.jupiter.api.Assertions.*;

import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SsgPointBatchServiceTest {

  @Autowired
  SsgPointBatchService ssgPointBatchService;

  @Test
  void updateProcesserFail() {
    ssgPointBatchService.updateProcesserFail(5408227299L , "G","S");
  }
}