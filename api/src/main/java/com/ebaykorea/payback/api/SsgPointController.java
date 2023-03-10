package com.ebaykorea.payback.api;

import com.ebaykorea.payback.api.dto.SaveSsgPointRequest;
import com.ebaykorea.payback.api.dto.common.CommonResponse;
import com.ebaykorea.payback.core.ssgpoint.service.SsgPointGmarketService;
import com.ebaykorea.payback.infrastructure.persistence.mapper.dto.SsgPointTargetResponseDto;
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

@Tag(name = "SSGPoint", description = "SSG 포인트 관련 Api")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SsgPointController {

 private final SsgPointGmarketService ssgPointGmarketService;
 private final SsgTokenQuery ssgTokenQuery;

  @Cacheable(cacheNames = "COMMON_KEY", key = "#name")
  @GetMapping("/ssgpoint/auth-token")
  public String getApiToken() {
    return ssgTokenQuery.getSsgAuthToken();
  }

  @PostMapping("/ssgpoint/save")
  public List<SsgPointTargetResponseDto> saveSsgPoint(final @Valid @RequestBody SaveSsgPointRequest saveSsgPointRequest) {
   return ssgPointGmarketService.setSsgPoint(saveSsgPointRequest);
  }

  @PostMapping("/ssgpoint/cancel")
  public CommonResponse cancelSsgPoint() {
    return null;
  }
}
