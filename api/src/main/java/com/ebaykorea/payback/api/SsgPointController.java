package com.ebaykorea.payback.api;

import static com.ebaykorea.payback.core.domain.constant.ResponseMessageType.SSGPOINT_CANCELED;
import static com.ebaykorea.payback.core.domain.constant.ResponseMessageType.SSGPOINT_CREATED;

import com.ebaykorea.payback.core.dto.common.CommonResponse;
import com.ebaykorea.payback.core.dto.CancelSsgPointRequestDto;
import com.ebaykorea.payback.core.dto.SaveSsgPointRequestDto;
import com.ebaykorea.payback.core.service.SsgPointService;
import com.ebaykorea.payback.core.dto.SsgPointTargetResponseDto;
import com.ebaykorea.payback.infrastructure.query.SsgTokenQuery;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "SSG Point", description = "SSG 포인트 관련 Api")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ssg-points")
public class SsgPointController {

 private final SsgPointService ssgPointService;
 private final SsgTokenQuery ssgTokenQuery;

  @Cacheable(cacheNames = "COMMON_KEY", key = "#name")
  @GetMapping("/auth-token")
  public String getApiToken() {
    return ssgTokenQuery.getSsgAuthToken();
  }

  @PostMapping
  public CommonResponse<List<SsgPointTargetResponseDto>> earnPoint(final @Valid @RequestBody SaveSsgPointRequestDto request) {
   return CommonResponse.success(SSGPOINT_CREATED ,ssgPointService.earnPoint(request));
  }

  @PostMapping("/{packNo}/cancel")
  public CommonResponse<List<SsgPointTargetResponseDto>> cancelPoint(@PathVariable Long packNo, final @Valid @RequestBody CancelSsgPointRequestDto request) {
   return CommonResponse.success(SSGPOINT_CANCELED , ssgPointService.cancelPoint(packNo, request));
  }
}
