package com.ebaykorea.payback.infrastructure.query.gmkt;

import com.ebaykorea.payback.core.query.SmilePointTradeQuery;
import com.ebaykorea.payback.infrastructure.query.data.SmilePointTradeQueryResult;
import com.ebaykorea.payback.infrastructure.query.mapper.SmilePointTradeMapper;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.customer.SmilePointTradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.GMARKET_TENANT;
import static java.util.stream.Collectors.toUnmodifiableList;

@Profile(GMARKET_TENANT)
@Service
@RequiredArgsConstructor
public class GmarketSmilePointTradeQuery implements SmilePointTradeQuery {
  private final SmilePointTradeMapper smilePointTradeMapper;
  private final SmilePointTradeRepository smilePointTradeRepository;

  @Override
  public SmilePointTradeQueryResult getSmilePointTradeBySmilePayNo(long smilePayNo) {
    return smilePointTradeRepository.findBySmilePayNo(smilePayNo)
        .map(smilePointTradeMapper::map)
        .orElse(null);
  }

  @Override
  public List<SmilePointTradeQueryResult> findSmilePointTradesByContrNo(String buyerNo, long contrNo) {
    return smilePointTradeRepository.findByContrNo(buyerNo, contrNo).stream()
        .map(smilePointTradeMapper::map)
        .collect(toUnmodifiableList());
  }

  @Override
  public List<SmilePointTradeQueryResult> findHistories(String buyerNo, String startDate, String endData, int maxRowCount) {
    return smilePointTradeRepository.findHistories(buyerNo, startDate, endData, maxRowCount).stream()
        .map(smilePointTradeMapper::map)
        .collect(toUnmodifiableList());
  }
}
