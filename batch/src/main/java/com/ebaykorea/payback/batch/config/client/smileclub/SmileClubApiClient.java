package com.ebaykorea.payback.batch.config.client.smileclub;

import com.ebaykorea.payback.batch.config.DefaultFeignConfig;
import com.ebaykorea.payback.batch.config.client.smileclub.dto.SmileClubSsgPointResponse;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "smileClubApiClient",
        url = "${apis.smileclub.url}",
        configuration = DefaultFeignConfig.class
)
public interface SmileClubApiClient {

    @Retry(name = "retryApi")
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/partner/ssgpoint",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    SmileClubSsgPointResponse getCardNo(
        @RequestParam(name="partnerId") final String partnerId,
        @RequestParam(name="partnerMembKey") final String partnerMembKey
    );
}
