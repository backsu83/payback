package com.ebaykorea.payback.batch.job.listener;

import com.ebaykorea.payback.batch.domain.SsgPointTargetDto;
import com.ebaykorea.payback.batch.repository.opayreward.SsgPointTargetRepositorySupport;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class SsgPointProcesserListener implements ItemProcessListener<SsgPointTargetEntity, SsgPointTargetDto> {

  private final SsgPointTargetRepositorySupport ssgPointTargetRepositorySupport;

  @Override
  public void beforeProcess(final SsgPointTargetEntity item) {

  }

  @Override
  public void afterProcess(final SsgPointTargetEntity item, final SsgPointTargetDto result) {

  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void onProcessError(final SsgPointTargetEntity item, final Exception e) {
    var result = updateProcesserFail(item.getOrderNo() ,
        item.getSiteType(),
        item.getTradeType(),
        e.getMessage());
    log.error("item processer error [{}][{}][{}]", item.getOrderNo(), result ,e.getLocalizedMessage());
  }

  public long updateProcesserFail(final long orderNo,
      final String orderSiteType,
      final String tradeType,
      final String errorCode
  ) {
    return ssgPointTargetRepositorySupport.updatePrcoesserFailBy(orderNo , orderSiteType , tradeType , errorCode);
  }

}
