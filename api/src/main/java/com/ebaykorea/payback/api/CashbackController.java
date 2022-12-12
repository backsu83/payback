package com.ebaykorea.payback.api;

import com.ebaykorea.payback.api.dto.CashbackResponseDto;
import com.ebaykorea.payback.api.dto.SaveCashbackRequestDto;
import com.ebaykorea.payback.api.dto.common.CommonResponse;
import com.ebaykorea.payback.core.CashbackApplicationService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(value = "CashbackController")
@RequestMapping("/api")
public class CashbackController {

  private final CashbackApplicationService applicationService;

  /**
   * 캐시백 데이터 저장
   *
   * @param request
   * @return
   */
  @PostMapping("/cashbacks")
  public CommonResponse<CashbackResponseDto> saveCashbacks(final @Valid @RequestBody SaveCashbackRequestDto request) {
    final var responseMessageType = applicationService.setCashback(request.getTxKey(), request.getOrderKey());

    return CommonResponse.success(responseMessageType, CashbackResponseDto.of(request.getTxKey(), request.getOrderKey()));
  }
}

