package com.ebaykorea.payback.core.repository;

public interface SmilePointTradeRepository {
  long save(String custNo,
            int point,
            int reasonCode,
            long contrNo,
            String comment,
            int ersNo,
            int eId,
            int appStatus,
            int targetType,
            long win_no,
            String sellerId);
}
