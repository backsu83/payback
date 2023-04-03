package com.ebaykorea.payback.api;

import static com.ebaykorea.payback.core.domain.constant.ResponseMessageType.SSGPOINT_CANCELED;
import static com.ebaykorea.payback.core.domain.constant.ResponseMessageType.SSGPOINT_CREATED;

import com.ebaykorea.payback.api.dto.common.CommonResponse;
import com.ebaykorea.payback.api.dto.common.SsgPointResponse;
import com.ebaykorea.payback.core.dto.CancelSsgPointRequestDto;
import com.ebaykorea.payback.core.dto.SaveSsgPointRequestDto;
import com.ebaykorea.payback.core.dto.UpdateSsgPointTradeStatusRequestDto;
import com.ebaykorea.payback.core.ssgpoint.service.SsgPointService;
import com.ebaykorea.payback.core.dto.SsgPointTargetResponseDto;
import com.ebaykorea.payback.infrastructure.query.SsgTokenQuery;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "SSG Point", description = "SSG 포인트 관련 Api")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SsgPointController {

 private final SsgPointService ssgPointService;
 private final SsgTokenQuery ssgTokenQuery;


  @GetMapping("/ssgpoint/auth-token")
  public String getApiToken() {
    return ssgTokenQuery.getSsgAuthToken();
  }

  @PostMapping("/ssgpoint/save")
  public SsgPointResponse<List<SsgPointTargetResponseDto>> earnPoint(final @Valid @RequestBody SaveSsgPointRequestDto request) {
   return new SsgPointResponse("0000", "success", ssgPointService.earnPoint(request));
  }

  @PostMapping("/ssgpoint/cancel")
  public SsgPointResponse<List<SsgPointTargetResponseDto>> cancelPoint(final @Valid @RequestBody CancelSsgPointRequestDto request) {
   return new SsgPointResponse("0000", "success", ssgPointService.cancelPoint(request));
  }

 @PostMapping("/ssgpoint/retryFailPointStatus")
 public SsgPointResponse<List<SsgPointTargetResponseDto>> retryFailPointStatus(final @Valid @RequestBody UpdateSsgPointTradeStatusRequestDto request) {
  return new SsgPointResponse("0000", "success",
          ssgPointService.retryFailPointStatus( request));
 }
}
