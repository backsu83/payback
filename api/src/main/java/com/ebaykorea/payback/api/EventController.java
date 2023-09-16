package com.ebaykorea.payback.api;

import com.ebaykorea.payback.core.dto.common.CommonResponse;
import com.ebaykorea.payback.core.dto.member.MemberCashbackRequestDto;
import com.ebaykorea.payback.core.dto.member.MemberCashbackResponseDto;
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

  @Operation(summary = "회원 별 캐시백 적립 요청", description = "결제번호 별 적립 금액으로 적립 요청")
  @PostMapping("/members/{member-key}/cashback")
  public CommonResponse<MemberCashbackResponseDto> cashback(
      final @PathVariable(value = "member-key") String memberKey,
      final @Valid @RequestBody List<MemberCashbackRequestDto> requests) {
    final var result = repository.save(memberKey, requests);
    return CommonResponse.success(SUCCESS,
        MemberCashbackResponseDto.builder()
            .memberKey(memberKey)
            .cashbackResults(result)
            .build());
  }
}
