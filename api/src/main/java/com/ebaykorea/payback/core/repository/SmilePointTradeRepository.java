package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.entity.smilepoint.SmilePointTrade;
import com.ebaykorea.payback.infrastructure.persistence.repository.customer.entity.SmilePointTradeEntity;

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
  SmilePointTradeEntity SelectSmilePointTradeBySmilePayNo(long smilePayNo);

  List<SmilePointTradeEntity> SelectSmilePointTradeByContrNo(String buyerNo, long contrNo);

  List<SmilePointTradeEntity> SelectHistory(String buyerNo, String startDate, String endData, int maxRowCount);
}
