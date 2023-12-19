package com.ebaykorea.payback.api;

import com.ebaykorea.payback.api.dto.toss.TossCommonResponseDto;
import com.ebaykorea.payback.api.dto.toss.TossRewardRequestDto;
import com.ebaykorea.payback.api.dto.toss.TossRewardResultRequestDto;
import com.ebaykorea.payback.api.mapper.TossRewardMapper;
import com.ebaykorea.payback.core.TossEventRewardApplicationService;
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

  private final TossEventRewardApplicationService service;
  private final TossRewardMapper mapper;

  private static final String SUCCESS = "SUCCESS";

  @Operation(summary = "토스 리워드 적립 요청", description = "토스 리워드 적립")
  @PostMapping
  public TossCommonResponseDto saveEventReward(
      final @Valid @RequestBody TossRewardRequestDto request) {
    final var result = service.saveEventReward(mapper.map(request));

    return TossCommonResponseDto.builder()
        .resultType(SUCCESS)
        .result(mapper.map(result))
        .build();
  }

  @Operation(summary = "토스 리워드 적립 요청 결과 조회")
  @PostMapping("/get-result")
  public TossCommonResponseDto getEventReward(
      final @Valid @RequestBody TossRewardResultRequestDto request) {
    final var result = service.getEventReward(mapper.map(request));

    return TossCommonResponseDto.builder()
        .resultType(SUCCESS)
        .result(mapper.map(result))
        .build();
  }

  @ExceptionHandler(value = {Exception.class})
  public TossCommonResponseDto handleException(Exception ex) {
    log.error(ex.getLocalizedMessage(), ex);

    return TossCommonResponseDto.builder()
        .resultType("FAIL")
        .errorCode("FAILED")
        .errorMessage("처리 중 오류 발생")
        .build();
  }

  @ExceptionHandler(value = {PaybackException.class})
  public TossCommonResponseDto handlePaybackException(final PaybackException ex) {
    log.error(ex.getLocalizedMessage(), ex);
    return TossCommonResponseDto.builder()
        .resultType("FAIL")
        .errorCode("FAILED")
        .errorMessage(ex.getMessage())
        .build();
  }
}
