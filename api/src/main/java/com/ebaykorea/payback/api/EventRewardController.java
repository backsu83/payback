package com.ebaykorea.payback.api;

import static com.ebaykorea.payback.core.domain.constant.ResponseMessageType.SUCCESS;

import com.ebaykorea.payback.api.mapper.EventRewardMapper;
import com.ebaykorea.payback.core.dto.common.CommonResponse;
import com.ebaykorea.payback.api.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.EventRewardResultDto;
import com.ebaykorea.payback.core.repository.SmileCashEventRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
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
  private final EventRewardMapper mapper;

  @Operation(summary = "이벤트 리워드 적립 요청", description = "요청 번호 별 적립 금액으로 적립 요청")
  @PostMapping
  public CommonResponse<EventRewardResultDto> saveEventReward(
      final @Valid @RequestBody EventRewardRequestDto request) {
    final var result = repository.saveWithBudget(mapper.map(request)).orElse(null);
    return CommonResponse.success(SUCCESS, result);
  }
}
