package com.ebaykorea.payback.infrastructure.persistence.repository;

import com.ebaykorea.payback.core.domain.entity.smilepoint.SmilePointTrade;
import com.ebaykorea.payback.core.repository.SmilePointTradeRepository;
import com.ebaykorea.payback.infrastructure.persistence.mapper.SmilePointTradeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SmilePointRepositoryImpl implements SmilePointTradeRepository {

  private final SmilePointTradeMapper smilePointTradeMapper;

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
  public SmilePointTrade SelectSmilePointTradeBySmilePayNo(long smilePayNo) {
    var result = smilePointTradeRepository.SelectBySmilePayNo(smilePayNo);
    return smilePointTradeMapper.map(result);
  }

  @Override
  public List<SmilePointTrade> SelectSmilePointTradeByContrNo(String buyerNo, long contrNo) {
    var result = smilePointTradeRepository.SelectByContrNo(buyerNo, contrNo);
    return smilePointTradeMapper.map(result);
  }

  @Override
  public List<SmilePointTrade> SelectHistory(String buyerNo, String startDate, String endData, int maxRowCount) {
    var result =  smilePointTradeRepository.SelectHistory(buyerNo, startDate, endData, maxRowCount);
    return smilePointTradeMapper.map(result);
  }
}
