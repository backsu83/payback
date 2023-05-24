package com.ebaykorea.payback.api;

import com.ebaykorea.payback.api.dto.common.SsgPointResponse;
import com.ebaykorea.payback.core.dto.VerifyDailySsgPointDto;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointTarget;
import com.ebaykorea.payback.core.dto.common.CommonResponse;
import com.ebaykorea.payback.core.dto.ssgpoint.*;
import com.ebaykorea.payback.core.service.SsgPointCancelService;
import com.ebaykorea.payback.infrastructure.query.SsgPointQuery;
import com.ebaykorea.payback.core.service.SsgPointService;
import com.ebaykorea.payback.infrastructure.query.data.SsgPointTargetQueryResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

import static com.ebaykorea.payback.core.domain.constant.ResponseMessageType.*;

@Tag(name = "SSG Point", description = "SSG 포인트 관련 Api")
@Slf4j
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
  public CommonResponse<SsgPointTarget> retryFailPointStatus(@PathVariable(value = "order-no") Long orderNo, final @Valid @RequestBody UpdateSsgPointTradeStatusRequestDto request) {
   return CommonResponse.success(SSGPOINT_RETRIED, ssgPointService.retryFailed(orderNo, request));
  }

  @GetMapping
  public List<SsgPointTargetQueryResult> getSsgPoints(Long packNo, String siteType) {
   return ssgPointQuery.getSsgPointQueryResult(packNo, siteType);
  }
}
