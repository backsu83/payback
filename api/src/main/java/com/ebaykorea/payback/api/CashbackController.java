package com.ebaykorea.payback.api;

import com.ebaykorea.payback.core.dto.cashback.CashbackResponseDto;
import com.ebaykorea.payback.core.dto.cashback.SaveCashbackRequestDto;
import com.ebaykorea.payback.core.dto.common.CommonResponse;
import com.ebaykorea.payback.core.service.CashbackApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.GMARKET_TENANT;

@Profile(GMARKET_TENANT)
@Tag(name = "Cashback", description = "캐시백 관련 Api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CashbackController {

  private final CashbackApplicationService applicationService;

  @PostMapping("/cashbacks")
  public CommonResponse<CashbackResponseDto> saveCashbacks(final @Valid @RequestBody SaveCashbackRequestDto request) {
    final var responseMessageType = applicationService.setCashback(request.getTxKey(), request.getOrderKey());

    return CommonResponse.success(responseMessageType, CashbackResponseDto.of(request.getTxKey(), request.getOrderKey()));
  }

}

