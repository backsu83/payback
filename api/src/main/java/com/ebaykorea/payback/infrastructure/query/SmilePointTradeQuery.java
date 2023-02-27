package com.ebaykorea.payback.infrastructure.query;

import com.ebaykorea.payback.infrastructure.query.data.SmilePointTradeQueryResult;
import com.ebaykorea.payback.infrastructure.query.mapper.SmilePointTradeMapper;
import com.ebaykorea.payback.infrastructure.persistence.repository.customer.SmilePointTradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@RequiredArgsConstructor
public class SmilePointTradeQuery {
  private final SmilePointTradeMapper smilePointTradeMapper;
  private final SmilePointTradeRepository smilePointTradeRepository;

  public SmilePointTradeQueryResult getSmilePointTradeBySmilePayNo(long smilePayNo) {
    return smilePointTradeRepository.findBySmilePayNo(smilePayNo)
        .map(smilePointTradeMapper::map)
        .orElse(null);
  }

  public List<SmilePointTradeQueryResult> findSmilePointTradesByContrNo(String buyerNo, long contrNo) {
    return smilePointTradeRepository.findByContrNo(buyerNo, contrNo).stream()
        .map(smilePointTradeMapper::map)
        .collect(toUnmodifiableList());
  }

  public List<SmilePointTradeQueryResult> findHistories(String buyerNo, String startDate, String endData, int maxRowCount) {
    return smilePointTradeRepository.findHistories(buyerNo, startDate, endData, maxRowCount).stream()
        .map(smilePointTradeMapper::map)
        .collect(toUnmodifiableList());
  }
}
