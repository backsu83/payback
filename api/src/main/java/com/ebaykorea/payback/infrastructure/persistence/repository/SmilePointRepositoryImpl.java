package com.ebaykorea.payback.infrastructure.persistence.repository;

import com.ebaykorea.payback.core.repository.SmilePointTradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmilePointRepositoryImpl implements SmilePointTradeRepository {

  private final com.ebaykorea.payback.infrastructure.persistence.repository.customer.SmilePointTradeRepository smilePointTradeRepository;

  @Override
  public long save(String custNo,
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
}
