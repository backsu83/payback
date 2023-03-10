package com.ebaykorea.payback.core.service;


import com.ebaykorea.payback.core.domain.entity.smilepoint.SmilePointTrade;
import com.ebaykorea.payback.core.repository.SmilePointTradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmilePointApplicationService {
  private final SmilePointTradeRepository smilePointTradeRepository;

  public long setSmilePoint(String custNo,
                            int point,
                            int reasonCode,
                            long contrNo,
                            String comment,
                            int ersNo,
                            int eId,
                            int apprStaus,
                            int targetType,
                            long winNo,
                            String sellerId) {
    return smilePointTradeRepository.setSmilePoint(custNo, point,
    reasonCode,
    contrNo,
    comment,
    ersNo,
    eId,
    apprStaus,
    targetType,
    winNo,
    sellerId);
  }

  public SmilePointTrade SelectSmilePointTradeBySmilePayNo(long smilePayNo) {
    return smilePointTradeRepository.SelectSmilePointTradeBySmilePayNo(smilePayNo);
  }

  public List<SmilePointTrade> SelectSmilePointTradeByContrNo(String buyerNo, long contrNo) {
    var result = smilePointTradeRepository.SelectSmilePointTradeByContrNo(buyerNo, contrNo);
    if (result == null) {
      return null;
    }
    if (result.size() < 1) {
      return null;
    }
    return result;
  }

  public List<SmilePointTrade> SelectHistory(String buyerNo, String startDate, String endData, int maxRowCount) {
    var result = smilePointTradeRepository.SelectHistory(buyerNo, startDate, endData, maxRowCount);
    if (result == null) {
      return null;
    }
    if (result.size() < 1) {
      return null;
    }
    return result;
  }
}
