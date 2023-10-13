package com.ebaykorea.payback.api;

import com.ebaykorea.payback.api.dto.toss.TossEventRewardRequestDto;
import com.ebaykorea.payback.api.dto.toss.TossEventRewardResponseDto;
import com.ebaykorea.payback.api.dto.toss.TossEventRewardResultRequestDto;
import com.ebaykorea.payback.api.mapper.TossEventRewardMapper;
import com.ebaykorea.payback.core.EventRewardApplicationService;
import com.ebaykorea.payback.core.dto.common.CommonResponse;
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.EventRewardResponseDto;
import com.ebaykorea.payback.core.dto.event.MemberEventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.MemberEventRewardResponseDto;
import com.ebaykorea.payback.core.repository.SmileCashEventRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.ebaykorea.payback.core.domain.constant.ResponseMessageType.SUCCESS;

@Slf4j
@Tag(name = "Event", description = "이벤트 리워드 적립")
@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventController {

  private final SmileCashEventRepository repository;
  private final EventRewardApplicationService service;
  private final TossEventRewardMapper mapper;

  @Operation(summary = "회원 별 이벤트 리워드 적립 요청", description = "요청 번호 별 적립 금액으로 적립 요청")
  @PostMapping("/members/{member-key}/cashback")
  public CommonResponse<MemberEventRewardResponseDto> saveEventRewardByMember(
      final @PathVariable(value = "member-key") String memberKey,
      final @Valid @RequestBody MemberEventRewardRequestDto request) {
    final var result = repository.save(memberKey, request).orElse(null);
    return CommonResponse.success(SUCCESS,
        MemberEventRewardResponseDto.builder()
            .memberKey(memberKey)
            .eventRewardResult(result)
            .build());
  }

  @Operation(summary = "토스 리워드 적립 요청", description = "토스 리워드 적립")
  @PostMapping("/rewards/toss")
  public TossEventRewardResponseDto saveEventReward(
      final @Valid @RequestBody TossEventRewardRequestDto request) {
    final var result = service.saveEventReward(mapper.map(request));
    return mapper.map(result);
  }

  @Operation(summary = "토스 리워드 적립 요청 결과 조회")
  @PostMapping("/rewards/toss/get-result")
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
}
