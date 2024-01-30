package com.ebaykorea.payback.api;

import static com.ebaykorea.payback.core.domain.constant.ResponseMessageType.SUCCESS;

import com.ebaykorea.payback.core.dto.common.CommonResponse;
import com.ebaykorea.payback.infrastructure.query.ReviewRewardQuery;
import com.ebaykorea.payback.infrastructure.query.data.ReviewRewardQueryResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Review Reward Query", description = "상품평 리워드 적립 조회")
@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewRewardQueryController {
  private final ReviewRewardQuery query;

  @Operation(summary = "상품평 리워드 조회", description = "상품평 리워드 조회")
  @GetMapping("/rewards")
  public CommonResponse<List<ReviewRewardQueryResult>> getReviewReward(final String memberKey , final Long requestNo) {
    return CommonResponse.success(SUCCESS, query.getReviewReward(memberKey , requestNo));
  }
}
