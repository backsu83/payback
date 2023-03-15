package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.entity.smilepoint.SmilePointTrade;

import java.util.List;

public interface SmilePointTradeRepository {
  long setSmilePoint(String custNo,
                            int point,
                            int reasonCode,
                            long contrNo,
                            String comment,
                            int ersNo,
                            int eId,
                            int apprStaus,
                            int targetType,
                            long winNo,
                            String sellerId);
  SmilePointTrade SelectSmilePointTradeBySmilePayNo(long smilePayNo);

  List<SmilePointTrade> SelectSmilePointTradeByContrNo(String buyerNo, long contrNo);

  List<SmilePointTrade> SelectHistory(String buyerNo, String startDate, String endData, int maxRowCount);
}
