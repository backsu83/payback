package com.ebaykorea.payback.api;

import com.ebaykorea.payback.api.dto.toss.TossEventRewardRequestDto;
import com.ebaykorea.payback.api.dto.toss.TossEventRewardResponseDto;
import com.ebaykorea.payback.api.dto.toss.TossEventRewardResultRequestDto;
import com.ebaykorea.payback.api.mapper.TossEventRewardMapper;
import com.ebaykorea.payback.core.EventRewardApplicationService;
import com.ebaykorea.payback.core.exception.PaybackException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Tag(name = "Event", description = "이벤트 리워드 적립")
@RestController
@RequiredArgsConstructor
@RequestMapping("/event/rewards/toss")
public class EventRewardTossController {

  private final EventRewardApplicationService service;
  private final TossEventRewardMapper mapper;

  @Operation(summary = "토스 리워드 적립 요청", description = "토스 리워드 적립")
  @PostMapping
  public TossEventRewardResponseDto saveEventReward(
      final @Valid @RequestBody TossEventRewardRequestDto request) {
    final var result = service.saveEventReward(mapper.map(request));
    return mapper.map(result);
  }

  @Operation(summary = "토스 리워드 적립 요청 결과 조회")
  @PostMapping("/get-result")
  public TossEventRewardResponseDto getEventReward(
      final @Valid @RequestBody TossEventRewardResultRequestDto request) {
    final var result = service.getEventReward(mapper.map(request));
    return mapper.map(result);
  }

  @ExceptionHandler(value = {Exception.class})
  public TossEventRewardResponseDto handleException(Exception ex) {
    log.error(ex.getLocalizedMessage(), ex);
    return new TossEventRewardResponseDto("", "FAILED", "처리 중 오류 발생");
  }

  @ExceptionHandler(value = {PaybackException.class})
  public TossEventRewardResponseDto handlePaybackException(final PaybackException ex) {
    log.error(ex.getLocalizedMessage(), ex);
    return new TossEventRewardResponseDto("", "FAILED", ex.getMessage());
  }
}
