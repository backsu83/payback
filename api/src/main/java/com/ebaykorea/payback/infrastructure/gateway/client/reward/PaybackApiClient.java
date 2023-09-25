package com.ebaykorea.payback.infrastructure.gateway.client.reward;

import com.ebaykorea.payback.core.dto.common.CommonResponse;
import com.ebaykorea.payback.core.dto.event.MemberEventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.MemberEventRewardResponseDto;
import com.ebaykorea.payback.infrastructure.gateway.client.config.DefaultFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@FeignClient(
    value = "paybackApiClient",
    url = "${apis.payback-another-tenant.url}",
    configuration = DefaultFeignConfig.class
)
public interface PaybackApiClient {
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/event/members/{member-key}/cashback"
  )
  Optional<CommonResponse<MemberEventRewardResponseDto>> eventSaveByMember(
      @PathVariable(value = "member-key") String memberKey,
      @RequestBody List<MemberEventRewardRequestDto> requests);
}
