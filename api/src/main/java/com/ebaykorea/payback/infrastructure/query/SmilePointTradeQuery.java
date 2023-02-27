package com.ebaykorea.payback.infrastructure.query;

import com.ebaykorea.payback.infrastructure.query.data.SmilePointTradeQueryData;
import com.ebaykorea.payback.infrastructure.query.mapper.SmilePointTradeQueryDataMapper;
import com.ebaykorea.payback.infrastructure.persistence.repository.customer.SmilePointTradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@RequiredArgsConstructor
public class SmilePointTradeQuery {
  private final SmilePointTradeQueryDataMapper smilePointTradeQueryDataMapper;
  private final SmilePointTradeRepository smilePointTradeRepository;

  public SmilePointTradeQueryData getSmilePointTradeBySmilePayNo(long smilePayNo) {
    return smilePointTradeRepository.findBySmilePayNo(smilePayNo)
        .map(smilePointTradeQueryDataMapper::map)
        .orElse(null);
  }

  public List<SmilePointTradeQueryData> findSmilePointTradesByContrNo(String buyerNo, long contrNo) {
    return smilePointTradeRepository.findByContrNo(buyerNo, contrNo).stream()
        .map(smilePointTradeQueryDataMapper::map)
        .collect(toUnmodifiableList());
  }

  public List<SmilePointTradeQueryData> findHistories(String buyerNo, String startDate, String endData, int maxRowCount) {
    return smilePointTradeRepository.findHistories(buyerNo, startDate, endData, maxRowCount).stream()
        .map(smilePointTradeQueryDataMapper::map)
        .collect(toUnmodifiableList());
  }
}
