package com.ebaykorea.payback.infrastructure.query;

import com.ebaykorea.payback.infrastructure.query.data.ReviewRewardQueryResult;
import java.util.List;

public interface ReviewRewardQuery {
  List<ReviewRewardQueryResult> getReviewReward(final String memberKey , final Long requestNo);
}
