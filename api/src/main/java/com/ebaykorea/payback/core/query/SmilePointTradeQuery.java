package com.ebaykorea.payback.core.query;

import com.ebaykorea.payback.infrastructure.query.data.SmilePointTradeQueryResult;

import java.util.List;

public interface SmilePointTradeQuery {
  SmilePointTradeQueryResult getSmilePointTradeBySmilePayNo(long smilePayNo);
  List<SmilePointTradeQueryResult> findSmilePointTradesByContrNo(String buyerNo, long contrNo);
  List<SmilePointTradeQueryResult> findHistories(String buyerNo, String startDate, String endData, int maxRowCount);
}
