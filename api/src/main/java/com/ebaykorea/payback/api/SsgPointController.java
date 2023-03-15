package com.ebaykorea.payback.api;

import com.ebaykorea.payback.api.dto.SaveSsgPointRequest;
import com.ebaykorea.payback.api.dto.common.CommonResponse;
import com.ebaykorea.payback.api.dto.mapper.SsgPointRequestMapper;
import com.ebaykorea.payback.core.ssgpoint.service.SsgPointService;
import com.ebaykorea.payback.core.dto.SsgPointTargetResponseDto;
import com.ebaykorea.payback.infrastructure.query.SsgPointQuery;
import com.ebaykorea.payback.infrastructure.query.SsgTokenQuery;
import com.ebaykorea.payback.infrastructure.query.data.SavedCashbackQueryResult;
import com.ebaykorea.payback.infrastructure.query.data.SsgPointTargetQueryResult;
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
@RequestMapping("/api")
public class SsgPointController {

 private final SsgPointService ssgPointService;
 private final SsgTokenQuery ssgTokenQuery;
 private final SsgPointQuery ssgPointQuery;
 private final SsgPointRequestMapper requestMapper;

  @Cacheable(cacheNames = "COMMON_KEY", key = "#name")
  @GetMapping("/ssgpoint/auth-token")
  public String getApiToken() {
    return ssgTokenQuery.getSsgAuthToken();
  }

  @PostMapping("/ssgpoint/save")
  public List<SsgPointTargetResponseDto> saveSsgPoint(final @Valid @RequestBody SaveSsgPointRequest saveSsgPointRequest) {
   return ssgPointService.setSsgPoint(requestMapper.map(saveSsgPointRequest));
  }

  @PostMapping("/ssgpoint/cancel")
  public CommonResponse cancelSsgPoint() {
    return null;
  }

  @GetMapping("/ssgpoint")
  public SsgPointTargetQueryResult getSsgPoint(@RequestParam(value = "packNo", required = false) final Long packNo, String siteType, String tradeType) {
   return ssgPointQuery.getSsgPointQueryResult(packNo, siteType, tradeType);
  }
}
