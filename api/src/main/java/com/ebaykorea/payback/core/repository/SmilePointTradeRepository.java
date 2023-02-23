package com.ebaykorea.payback.core.repository;

public interface SmilePointTradeRepositoryI {
  long save(String custNo,
            int point,
            int reasonCode,
            long contrNo,
            String comment,
            int ersNo,
            int eId,
            int apprStaus,
            int targetType,
            long win_no,
            String sellerId);
}
