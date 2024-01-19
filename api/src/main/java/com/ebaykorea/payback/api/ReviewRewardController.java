package com.ebaykorea.payback.api;

import static com.ebaykorea.payback.core.domain.constant.ResponseMessageType.SUCCESS;

import com.ebaykorea.payback.api.mapper.ReviewRewardMapper;
import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.dto.common.CommonResponse;
import com.ebaykorea.payback.core.dto.event.EventRewardResultDto;
import com.ebaykorea.payback.api.dto.review.ReviewRewardRequestDto;
import com.ebaykorea.payback.core.repository.SmileCashEventRepository;
import com.ebaykorea.payback.infrastructure.query.ReviewRewardQuery;
import com.ebaykorea.payback.infrastructure.query.data.ReviewRewardQueryResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Review", description = "상품평 리워드 적립")
@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewRewardController {

  private final SmileCashEventRepository repository;
  private final ReviewRewardMapper mapper;
  private final ReviewRewardQuery query;

  @Operation(summary = "상품평 리워드 적립", description = "일반 상품평 리워드 적립 요청")
  @PostMapping("/rewards")
  public CommonResponse<EventRewardResultDto> saveReviewRewards(final @Valid @RequestBody ReviewRewardRequestDto request) {
    return CommonResponse.success(SUCCESS, repository.save(mapper.map(request, EventType.Review)).orElse(null));
  }

  @Operation(summary = "프리미엄 상품평 리워드 적립", description = "프리미엄 상품평 리워드 적립 요청")
  @PostMapping("/premium-rewards")
  public CommonResponse<EventRewardResultDto> saveReviewRewardsPremium(final @Valid @RequestBody ReviewRewardRequestDto request) {
    return CommonResponse.success(SUCCESS, repository.save(mapper.map(request, EventType.ReviewPremium)).orElse(null));
  }

  @Operation(summary = "상품평 리워드 조회", description = "상품평 리워드 조회")
  @GetMapping("/rewards")
  public CommonResponse<List<ReviewRewardQueryResult>> getReviewReward(final String memberKey , final Long requestNo) {
    return CommonResponse.success(SUCCESS, query.getReviewReward(memberKey , requestNo));
  }
}
