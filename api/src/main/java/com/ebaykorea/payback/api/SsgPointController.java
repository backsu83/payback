package com.ebaykorea.payback.api;

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
  return CommonResponse.success(SSGPOINT_RETRIED, ssgPointService.retryFailPointStatus(orderNo, request));
 }

// @PostMapping("/dailyVerify")
// public SsgPointResponse<Object> ssgPointDailyVerify(final @Valid @RequestBody SsgPointVerifyRequestDto request) {
//   /*
//   To-Do
//   reqDate yyyymmdd 에 해당하는 날짜의
//   OrderSiteType 상점의
//   G 지마켓 대응 SSG 가맹점 ID
//   A 옥션 대응 SSG 가맹점 ID
//   PointTradeType 에 해당하는 총 건수/금액을 SSG로 전송한다
//   거래유형 코드
//   적립 S 대응 코드 000001 : 적립
//   취소 C 대응 코드 000002 : 적립취소
//   하루당 최대 4번의 호출
//   1. 지마켓 적립 건수 금액
//   2. 지마켓 취소 건수 금액
//   3. 옥션   적립 건수 금액
//   4. 옥션   취소 건수 금액
//   기본적으로 배치/스케쥴로 매일 정해진 시간대에 호출
//   미호출(실패)시 어디민을 통한 수동 호출
//   건수와 금액은 이 API에서 조회해서 처리한다 (수기입력X)
//    */
//  return new SsgPointResponse("0000", "success",
//          null);
// }
}
