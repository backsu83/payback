package com.ebaykorea.payback.consumer.client;

import com.ebaykorea.payback.consumer.client.dto.PaybackSsgPointCancelRequest;
import com.ebaykorea.payback.consumer.client.dto.PaybackSsgPointCancelResponse;
import java.util.Optional;

import com.ebaykorea.payback.consumer.client.dto.PaybackRequestDto;
import com.ebaykorea.payback.consumer.client.dto.PaybackResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
    value = "paybackApiClient",
    url = "${apis.payback.url}"
)
public interface PaybackApiClient {

  @RequestMapping(
      method = RequestMethod.POST,
      value = "/api/cashbacks",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  Optional<PaybackResponseDto> saveCashbacks(final @RequestBody PaybackRequestDto request);

  @RequestMapping(
      method = RequestMethod.POST,
      value = "/api/ssgpoint/cancel",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  Optional<PaybackSsgPointCancelResponse> cancelSsgPoint(final @RequestBody PaybackSsgPointCancelRequest request);


}
