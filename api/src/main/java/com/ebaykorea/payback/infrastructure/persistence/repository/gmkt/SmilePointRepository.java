package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt;

import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.customer.SmilePointTradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.GMARKET_TENANT;

@Profile(GMARKET_TENANT)
@Service
@RequiredArgsConstructor
public class SmilePointRepository {
  private final SmilePointTradeRepository smilePointTradeRepository;

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
}
