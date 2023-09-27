package com.ebaykorea.payback.api;

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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.ebaykorea.payback.core.domain.constant.ResponseMessageType.SUCCESS;

@Tag(name = "Event", description = "이벤트 적립")
@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventController {

  private final SmileCashEventRepository repository;
  private final EventRewardApplicationService service;

  @Operation(summary = "회원 별 캐시백 적립 요청", description = "요청 번호 별 적립 금액으로 적립 요청")
  @PostMapping("/members/{member-key}/cashback")
  public CommonResponse<MemberEventRewardResponseDto> eventSaveByMember(
      final @PathVariable(value = "member-key") String memberKey,
      final @Valid @RequestBody MemberEventRewardRequestDto request) {
    final var result = repository.save(memberKey, request).orElse(null);
    return CommonResponse.success(SUCCESS,
        MemberEventRewardResponseDto.builder()
            .memberKey(memberKey)
            .eventRewardResult(result)
            .build());
  }

  @Operation(summary = "이벤트 적립 요청", description = "이벤트 적립")
  @PostMapping("/rewards")
  public CommonResponse<EventRewardResponseDto> eventSave(
      final @Valid @RequestBody EventRewardRequestDto request) {
    return CommonResponse.success(SUCCESS, service.saveEventReward(request));
  }
}
