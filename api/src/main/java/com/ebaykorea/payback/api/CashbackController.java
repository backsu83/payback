package com.ebaykorea.payback.api;

import com.ebaykorea.payback.api.advice.LogExecutor;
import com.ebaykorea.payback.api.dto.CashbackOrderRequestDto;
import com.ebaykorea.payback.api.dto.CashbackRequestDto;
import com.ebaykorea.payback.api.dto.SaveCashbackRequestDto;
import com.ebaykorea.payback.core.CashbackApplicationService;
import com.ebaykorea.payback.infrastructure.data.cashback.CashbackOrderQuery;
import com.ebaykorea.payback.infrastructure.data.cashback.CashbackResponse;
import com.ebaykorea.payback.infrastructure.gateway.impl.RewardGatewayImpl;
import com.ebaykorea.payback.infrastructure.mapper.CashbackOrderMapper;
import com.ebaykorea.payback.infrastructure.mapper.CashbackRequestMapper;
import com.ebaykorea.payback.infrastructure.query.CashbackQuery;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

  private final CashbackQuery cashbackQuery;
  private final CashbackRequestMapper requestMapper;
  private final RewardGatewayImpl rewardGateway;
  private final CashbackOrderMapper cashbackOrderMapper;
  private final CashbackApplicationService applicationService;

  //TODO: 임시 코드 제거 예정
  /**
   * api 조회 테스트
   *
   * @param request
   * @return
   */
  @LogExecutor
  @PostMapping("/CashbackReward")
  public ResponseEntity<CashbackResponse> getCashbackReward(final @Validated @RequestBody CashbackRequestDto request) {
    return ResponseEntity.ok(rewardGateway.getCashbackReward(requestMapper.of(request)));
  }

  //TODO: 임시 코드 제거 예정
  /**
   * db + api 조회 테스트
   *
   * @param request
   * @return
   */
  @LogExecutor
  @PostMapping("/CashbackReward/order")
  public ResponseEntity<CashbackOrderQuery> getCashbackRewardOrder(final @Validated @RequestBody CashbackOrderRequestDto request) {
    return ResponseEntity.ok(cashbackQuery.getCashbackOrder(
        cashbackOrderMapper.of(request),
        requestMapper.of(request.getReward())));
  }

  /**
   * 캐시백 데이터 저장
   * @param request
   */
  @PostMapping("/cashbacks")
  public void saveCashbacks(final @RequestBody SaveCashbackRequestDto request) {
    applicationService.setCashback(request.getOrderKey());
  }
}

