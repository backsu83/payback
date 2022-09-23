package com.ebaykorea.payback.port.service.reward.client;


import com.ebaykorea.payback.port.service.config.DefaultFeignConfig;
import com.ebaykorea.payback.core.domain.entity.saturn.TestApiGroup;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(
        value = "testApiClient",
        url = "${apis.test.url}",
        configuration = DefaultFeignConfig.class
)
public interface TestApiClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api")
    List<TestApiGroup> apiGroupList();
}
