package com.ebaykorea.payback.batch.service;

import com.ebaykorea.payback.batch.domain.constant.PointStatusType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
class SsgPointBatchServiceTest {

  @Autowired
  SsgPointBatchService ssgPointBatchService;

  @Test
  void updateProcesserFail() {
    ssgPointBatchService.updateProcesserFail(5408227299L , "G","S", PointStatusType.Fail.getCode());
  }
}