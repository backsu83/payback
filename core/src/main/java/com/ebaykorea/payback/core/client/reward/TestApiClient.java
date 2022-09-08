package com.ebaykorea.payback.core.client.reward;


import com.ebaykorea.payback.core.config.client.DefaultFeignConfig;
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
