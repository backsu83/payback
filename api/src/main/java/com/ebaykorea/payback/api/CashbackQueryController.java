package com.ebaykorea.payback.api;

import com.ebaykorea.payback.core.domain.constant.ResponseMessageType;
import com.ebaykorea.payback.core.dto.common.CommonResponse;
import com.ebaykorea.payback.infrastructure.query.RewardTargetQuery;
import com.ebaykorea.payback.infrastructure.query.data.RewardTargetQueryResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.GMARKET_TENANT;

@Profile(GMARKET_TENANT)
@Tag(name = "RewardTarget", description = "리워드 대상 조회")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CashbackQueryController {

  private final RewardTargetQuery rewardTargetQuery;

  @GetMapping("/rewards")
  public CommonResponse<RewardTargetQueryResult> getRewardTargets(
      @RequestParam(value = "txKey") final String txKey,
      @RequestParam(value = "orderKey") final String orderKey
  ) {
    return CommonResponse.success(ResponseMessageType.SUCCESS, rewardTargetQuery.getSavedCashback(txKey, orderKey));
  }

  @GetMapping("/rewards/{pay-no}")
  public CommonResponse<RewardTargetQueryResult> getRewardTargets(
      @PathVariable(value = "pay-no") final Long payNo
  ) {
    return CommonResponse.success(ResponseMessageType.SUCCESS, rewardTargetQuery.getSavedCashback(payNo));
  }
}
