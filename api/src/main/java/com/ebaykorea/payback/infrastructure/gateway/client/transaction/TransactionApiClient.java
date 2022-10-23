package com.ebaykorea.payback.infrastructure.gateway.client.transaction;

import com.ebaykorea.payback.infrastructure.gateway.client.config.DefaultFeignConfig;
import com.ebaykorea.payback.infrastructure.gateway.client.transaction.dto.KeyMapDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
    value = "transactionApiClient",
    url = "${apis.transaction.url}",
    configuration = DefaultFeignConfig.class
)
public interface TransactionApiClient {
  //TODO: 현재 transaction-api에 orderKey로 조회하는 기능이 없어 추가 예정입니다
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/key/maps",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  List<KeyMapDto> findKeyMaps(@RequestParam("order-key")final String orderKey);
}
