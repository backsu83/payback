package com.ebaykorea.payback.batch.job.listener;

import com.ebaykorea.payback.batch.domain.SsgPointTargetDto;
import com.ebaykorea.payback.batch.repository.opayreward.SsgPointTargetRepositorySupport;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntity;
import com.google.common.base.CharMatcher;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class SsgPointProcessorListener implements ItemProcessListener<SsgPointTargetEntity, SsgPointTargetDto> {

  private final SsgPointTargetRepositorySupport ssgPointTargetRepositorySupport;

  @Override
  public void beforeProcess(final SsgPointTargetEntity item) {
    final var uuid = CharMatcher.anyOf("-").removeFrom(UUID.randomUUID().toString());
    MDC.put("moa.requestid", uuid);
  }

  @Override
  public void afterProcess(final SsgPointTargetEntity item, final SsgPointTargetDto result) {
    MDC.clear();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void onProcessError(final SsgPointTargetEntity item, final Exception e) {
    var result = updateProcessorFail(item.getOrderNo() ,
        item.getSiteType(),
        item.getTradeType(),
        e.getMessage());
    log.error("item processor error [{}][{}][{}]", item.getOrderNo(), result ,e.getLocalizedMessage());
  }

  public long updateProcessorFail(final long orderNo,
      final String orderSiteType,
      final String tradeType,
      final String errorCode
  ) {
    return ssgPointTargetRepositorySupport.updateItemPrcoessorFailure(orderNo , orderSiteType , tradeType , errorCode);
  }

}
