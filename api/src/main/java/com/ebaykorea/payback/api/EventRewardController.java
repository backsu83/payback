package com.ebaykorea.payback.api;

import com.ebaykorea.payback.core.dto.common.CommonResponse;
import com.ebaykorea.payback.core.dto.event.MemberEventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.MemberEventRewardResponseDto;
import com.ebaykorea.payback.core.dto.event.SetEventRewardRequestDto;
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
public class EventRewardController {

  private final SmileCashEventRepository repository;

  @Operation(summary = "회원 별 캐시백 적립 요청", description = "요청 번호 별 적립 금액으로 적립 요청")
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

  @Operation(summary = "이벤트 리워드 적립 데이터 변경", description = "스마일캐시 이벤트 데이터 변경")
  @PutMapping("/rewards/{smilepay-no}")
  public CommonResponse<Long> setEventReward(
      final @PathVariable(value = "smilepay-no") Long smilePayNo,
      final @Valid @RequestBody SetEventRewardRequestDto request) {
    repository.set(smilePayNo, request);
    return CommonResponse.success(SUCCESS, smilePayNo);
  }
}
