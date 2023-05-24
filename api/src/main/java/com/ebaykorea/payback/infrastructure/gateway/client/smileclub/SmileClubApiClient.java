package com.ebaykorea.payback.infrastructure.gateway.client.smileclub;

import com.ebaykorea.payback.infrastructure.gateway.client.config.DefaultFeignConfig;
import com.ebaykorea.payback.infrastructure.gateway.client.smileclub.dto.SmileClubMemberResponseDto;
import com.ebaykorea.payback.infrastructure.gateway.client.smileclub.dto.SmileClubSsgPointResponseDto;
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

    @RequestMapping(
        method = RequestMethod.GET,
        value = "/partner/ssgpoint",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    Optional<SmileClubSsgPointResponseDto> getCardNo(
        @RequestParam(name="partnerId") final String partnerId,
        @RequestParam(name="partnerMembKey") final String partnerMembKey
    );

    @RequestMapping(
        method = RequestMethod.GET,
        value = "/partner/member",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    SmileClubMemberResponseDto getMembers(
        @RequestParam("partnerId") final String partnerId,
        @RequestParam("partnerMembKey") final String partnerMembKey
    );
}
