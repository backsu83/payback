package com.ebaykorea.payback.batch.job.listener;

import com.ebaykorea.payback.batch.domain.SsgPointTargetDto;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntity;
import com.ebaykorea.payback.batch.service.SsgPointBatchService;
import com.ebaykorea.payback.batch.util.support.GsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SsgPointProcesserListener implements ItemProcessListener<SsgPointTargetEntity, SsgPointTargetDto> {

  private final SsgPointBatchService ssgPointBatchService;

  @Override
  public void beforeProcess(final SsgPointTargetEntity item) {

  }

  @Override
  public void afterProcess(final SsgPointTargetEntity item, final SsgPointTargetDto result) {

  }

  @Override
  public void onProcessError(final SsgPointTargetEntity item, final Exception e) {
    var result = ssgPointBatchService.updateProcesserFail(item.getOrderNo() ,
        item.getSiteType(),
        item.getTradeType(),
        e.getMessage());
    log.error("item processer error [{}][{}][{}]", item.getOrderNo(), result ,e.getLocalizedMessage());
  }
}
