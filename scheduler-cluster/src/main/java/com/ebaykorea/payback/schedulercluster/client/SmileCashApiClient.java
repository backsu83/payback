package com.ebaykorea.payback.schedulercluster.client;

import com.ebaykorea.payback.schedulercluster.client.dto.smilecash.MassSaveRequestDto;
import com.ebaykorea.payback.schedulercluster.client.dto.smilecash.MassSaveResponseDto;
import com.ebaykorea.payback.schedulercluster.config.DefaultFeignConfig;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
    value = "smile-cash",
    url = "${apis.smile-cash.url}",
    configuration = DefaultFeignConfig.class
)
public interface SmileCashApiClient {

  @RequestMapping(
      method = RequestMethod.POST,
      value = "/SmileCash/MassSave/AddInstantSave"
  )
  Optional<MassSaveResponseDto> requestMassSave(
      @RequestBody final MassSaveRequestDto request,
      @RequestHeader("Authorization") String userKey);


}
