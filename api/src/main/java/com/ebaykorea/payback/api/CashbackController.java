package com.ebaykorea.payback.api;

import com.ebaykorea.payback.api.dto.SaveCashbackRequestDto;
import com.ebaykorea.payback.core.CashbackApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "리워드 조회", description = "리워드 조회 테스트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CashbackController {

  private final CashbackApplicationService applicationService;

  /**
   * 캐시백 데이터 저장
   * @param request
   */
  @PostMapping("/cashbacks")
  public void saveCashbacks(final @RequestBody SaveCashbackRequestDto request) {
    applicationService.setCashback(request.getTxKey(), request.getOrderKey());
  }
}

