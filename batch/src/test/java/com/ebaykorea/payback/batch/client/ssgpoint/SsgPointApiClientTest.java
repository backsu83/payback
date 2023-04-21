package com.ebaykorea.payback.batch.client.ssgpoint;

import com.ebaykorea.payback.batch.config.properties.SsgPointAuthProperties;
import com.ebaykorea.payback.batch.domain.SsgPointCertifier;
import com.ebaykorea.payback.batch.domain.SsgPointVerifyDto;
import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import com.ebaykorea.payback.batch.domain.constant.VerifyTradeType;
import com.ebaykorea.payback.batch.service.SsgPointBatchService;
import com.ebaykorea.payback.batch.util.support.GsonUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SsgPointApiClientTest {

    @Autowired
    SsgPointApiClient ssgPointApiClient;

    @Autowired
    SsgPointAuthProperties authProperties;

    @Autowired
    SsgPointBatchService ssgPointBatchService;

    @Test
    void verifyPoint() {
       var gmktAuthInfo =  SsgPointCertifier.of(authProperties, OrderSiteType.Gmarket);
       final SsgPointVerifyDto gmktSaveResult = ssgPointBatchService.verify(gmktAuthInfo, OrderSiteType.Gmarket, VerifyTradeType.Save);
       //System.out.println(GsonUtils.toJsonPretty(gmktSaveResult));
       final long result = ssgPointBatchService.saveVerifySuceess(gmktSaveResult);
       System.out.println(GsonUtils.toJsonPretty(result));
    }
}