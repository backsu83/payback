package com.ebaykorea.payback.infrastructure.gateway.client.ssgpoint;


import com.ebaykorea.payback.infrastructure.gateway.client.config.DefaultFeignConfig;
import com.ebaykorea.payback.infrastructure.gateway.client.ssgpoint.dto.SsgPointAuthTokenRequestDto;
import com.ebaykorea.payback.infrastructure.gateway.client.ssgpoint.dto.SsgPointAuthTokenResponseDto;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
    name = "ssgPointApiClient",
    url = "${apis.ssgpoint.url}",
    configuration = DefaultFeignConfig.class
)
public interface SsgPointApiClient {

  @RequestMapping(
      method = RequestMethod.POST,
      value = "/authapi/auth",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  SsgPointAuthTokenResponseDto getAuthToken(@RequestBody final SsgPointAuthTokenRequestDto request);

}
