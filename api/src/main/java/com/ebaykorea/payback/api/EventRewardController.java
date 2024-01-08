package com.ebaykorea.payback.api;

import static com.ebaykorea.payback.core.domain.constant.ResponseMessageType.SUCCESS;

import com.ebaykorea.payback.core.dto.common.CommonResponse;
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.EventRewardResultDto;
import com.ebaykorea.payback.core.dto.event.SetEventRewardRequestDto;
import com.ebaykorea.payback.core.repository.SmileCashEventRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Event", description = "이벤트 리워드 적립")
@RestController
@RequiredArgsConstructor
@RequestMapping("/event/rewards")
public class EventRewardController {

  private final SmileCashEventRepository repository;

  @Operation(summary = "이벤트 리워드 적립 요청", description = "요청 번호 별 적립 금액으로 적립 요청")
  @PostMapping
  public CommonResponse<EventRewardResultDto> saveEventRewardByMember(
      final @Valid @RequestBody EventRewardRequestDto request) {
    final var result = repository.saveWithBudget(request).orElse(null);
    return CommonResponse.success(SUCCESS, result);
  }

  @Operation(summary = "이벤트 리워드 적립 데이터 변경", description = "스마일캐시 이벤트 데이터 변경")
  @PutMapping("/{smilepay-no}")
  public CommonResponse<Long> setEventReward(
      final @PathVariable(value = "smilepay-no") Long smilePayNo,
      final @Valid @RequestBody SetEventRewardRequestDto request) {
    repository.set(smilePayNo, request);
    return CommonResponse.success(SUCCESS, smilePayNo);
  }
}
