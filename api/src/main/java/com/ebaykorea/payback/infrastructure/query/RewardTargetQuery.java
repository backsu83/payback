package com.ebaykorea.payback.infrastructure.query;

import com.ebaykorea.payback.infrastructure.query.data.RewardTargetQueryResult;

public interface RewardTargetQuery {
  RewardTargetQueryResult getSavedCashback(final String txKey, final String orderKey);
  RewardTargetQueryResult getSavedCashback(final Long packNo);
}
