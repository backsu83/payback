package com.ebaykorea.payback.api;

import com.ebaykorea.payback.api.dto.common.SsgPointResponse;
import com.ebaykorea.payback.core.dto.VerifyDailySsgPointDto;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointTarget;
import com.ebaykorea.payback.core.dto.common.CommonResponse;
import com.ebaykorea.payback.core.dto.ssgpoint.*;
import com.ebaykorea.payback.core.service.SsgPointCancelService;
import com.ebaykorea.payback.core.service.SsgPointService;
import com.ebaykorea.payback.infrastructure.query.SsgTokenQuery;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.ebaykorea.payback.core.domain.constant.ResponseMessageType.*;

@Tag(name = "SSG Point", description = "SSG 포인트 관련 Api")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ssg-points")
public class SsgPointController {

 private final SsgPointService ssgPointService;
 private final SsgPointCancelService ssgPointCancelService;
 private final SsgTokenQuery ssgTokenQuery;

  @Cacheable(cacheNames = "COMMON_KEY", key = "#name")
  @GetMapping("/auth-token")
  public String getApiToken() {
    return ssgTokenQuery.getSsgAuthToken();
  }

  @PostMapping
  public CommonResponse<SsgPointTarget> earnPoint(final @Valid @RequestBody SaveSsgPointRequestDto request) {
   return CommonResponse.success(SSGPOINT_CREATED ,ssgPointService.earnPoint(request));
  }

  @PostMapping("/{order-no}/cancel")
  public CommonResponse<SsgPointTarget> cancelPoint(@PathVariable(value = "order-no") Long orderNo, final @Valid @RequestBody CancelSsgPointRequestDto request) {
   return CommonResponse.success(SSGPOINT_CANCELED , ssgPointCancelService.cancelPoint(orderNo, request));
  }

 @PutMapping("/{order-no}/retry")
 public CommonResponse<SsgPointTarget> retryFailPointStatus(@PathVariable(value = "order-no") Long orderNo, final @Valid @RequestBody UpdateSsgPointTradeStatusRequestDto request) {
  return CommonResponse.success(SSGPOINT_RETRIED, ssgPointService.retryFailed(orderNo, request));
 }

 @PostMapping("/dailyVerify")
 public SsgPointResponse<Object> ssgPointDailyVerify(final @Valid @RequestBody VerifyDailySsgPointDto request) {
  return new SsgPointResponse("0000", "success", ssgPointService.verifyDailyPoint(request));
 }
}
