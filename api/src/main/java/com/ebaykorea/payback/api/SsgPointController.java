package com.ebaykorea.payback.api;

import com.ebaykorea.payback.core.dto.common.CommonResponse;
import com.ebaykorea.payback.core.dto.ssgpoint.CancelSsgPointRequestDto;
import com.ebaykorea.payback.core.dto.ssgpoint.SaveSsgPointRequestDto;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointTarget;
import com.ebaykorea.payback.core.dto.ssgpoint.UpdateSsgPointTradeStatusRequestDto;
import com.ebaykorea.payback.core.service.SsgPointCancelService;
import com.ebaykorea.payback.core.service.SsgPointService;
import com.ebaykorea.payback.infrastructure.query.SsgPointQuery;
import com.ebaykorea.payback.infrastructure.query.data.SsgPointTargetQueryResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.ebaykorea.payback.core.domain.constant.ResponseMessageType.*;
import static com.ebaykorea.payback.core.domain.constant.TenantCode.GMARKET_TENANT;

@Profile(GMARKET_TENANT)
@Tag(name = "SSG Point", description = "SSG 포인트 관련 Api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ssg-points")
public class SsgPointController {

 private final SsgPointService ssgPointService;
 private final SsgPointQuery ssgPointQuery;
 private final SsgPointCancelService ssgPointCancelService;

  @PostMapping
  public CommonResponse<SsgPointTarget> earnPoint(final @Valid @RequestBody SaveSsgPointRequestDto request) {
   return CommonResponse.success(SSGPOINT_CREATED ,ssgPointService.earnPoint(request));
  }

  @PostMapping("/{order-no}/cancel")
  public CommonResponse<SsgPointTarget> cancelPoint(@PathVariable(value = "order-no") Long orderNo, final @Valid @RequestBody CancelSsgPointRequestDto request) {
   return CommonResponse.success(SSGPOINT_CANCELED , ssgPointCancelService.cancelPoint(orderNo, request));
  }

  @PutMapping("/{order-no}/retry")
  public CommonResponse<SsgPointTarget> retryFailResponseCode(@PathVariable(value = "order-no") Long orderNo, final @Valid @RequestBody UpdateSsgPointTradeStatusRequestDto request) {
   return CommonResponse.success(SSGPOINT_RETRIED, ssgPointService.retryFailed(orderNo, request));
  }

  @Deprecated
  @GetMapping
  public List<SsgPointTargetQueryResult> getSsgPoints(Long packNo, String siteType) {
   return ssgPointQuery.getSsgPointQueryResult(packNo, siteType);
  }
}
