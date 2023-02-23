package com.ebaykorea.payback.infrastructure.persistence.repository;

import com.ebaykorea.payback.core.repository.SmilePointTradeRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.customer.entity.SmilePointTradeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SmilePointRepositoryImpl implements SmilePointTradeRepository {

  private final com.ebaykorea.payback.infrastructure.persistence.repository.customer.SmilePointTradeRepository smilePointTradeRepository;

  @Override
  public long setSmilePoint(String custNo, int point, int reasonCode, long contrNo, String comment, int ersNo, int eId, int apprStaus, int targetType, long winNo, String sellerId) {
    return smilePointTradeRepository.save(custNo,
            point,
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

  @Override
  public SmilePointTradeEntity SelectSmilePointTradeBySmilePayNo(long smilePayNo) {
    return smilePointTradeRepository.SelectBySmilePayNo(smilePayNo);
  }

  @Override
  public List<SmilePointTradeEntity> SelectSmilePointTradeByContrNo(String buyerNo, long contrNo) {
    return smilePointTradeRepository.SelectByContrNo(buyerNo, contrNo);
  }

  @Override
  public List<SmilePointTradeEntity> SelectHistory(String buyerNo, String startDate, String endData, int maxRowCount) {
    return smilePointTradeRepository.SelectHistory(buyerNo, startDate, endData, maxRowCount);
  }
}
