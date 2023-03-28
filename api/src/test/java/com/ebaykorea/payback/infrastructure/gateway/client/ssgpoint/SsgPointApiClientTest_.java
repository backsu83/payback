package com.ebaykorea.payback.infrastructure.gateway.client.ssgpoint;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.ssgpoint.state.SsgPointStateDelegate;
import com.ebaykorea.payback.infrastructure.gateway.client.ssgpoint.dto.SsgPointAuthTokenRequestDto;
import com.ebaykorea.payback.infrastructure.gateway.client.ssgpoint.dto.SsgPointExpectPointRequestDto;
import com.ebaykorea.payback.infrastructure.gateway.client.ssgpoint.dto.SsgPointPayInfoDto;
import com.ebaykorea.payback.util.support.GsonUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class SsgPointApiClientTest_ {

    @Autowired
    SsgPointApiClient ssgPointApiClient;

    @Autowired
    SsgPointStateDelegate ssgPointStateDelegate;

    @Test
    void getAuthToken() {
       var result =  ssgPointApiClient.getAuthToken(SsgPointAuthTokenRequestDto.builder()
                .clientId("49E615F309BC23C5CA7E4603E2036977")
                .apiKey("E320844B8E294F3E8D69395737C8B194")
                .build());
        System.out.println(GsonUtils.toJsonPretty(result));

    }

    @Test
    void getExpectPointAmount() {
//        var auth = ssgPointStateDelegate.find(OrderSiteType.Gmarket).auth();
//        var request = SsgPointExpectPointRequestDto.builder()
//                .clientId(auth.getClientId())
//                .apiKey(auth.getApiKey())
//                .tokenId("40560ed0230370f720124610190396f375abf10b904c")
//                .pntNoAddProdAmt(BigDecimal.ZERO)
//                .totAmt(BigDecimal.valueOf(1000L))
//                .brchId("B200042500")
//                .reqTrcNo("GMK230319091952S7268")
//                .payinfo(Lists.newArrayList(SsgPointPayInfoDto.builder()
//                        .payGb("00")
//                        .payType("A00011")
//                        .payAmt(BigDecimal.valueOf(1000L))
//                        .build()))
//                .build();
//        var result = ssgPointApiClient.getExpectPoint(request);
//        System.out.println(GsonUtils.toJsonPretty(request));
//        System.out.println(GsonUtils.toJsonPretty(result));
    }


}
