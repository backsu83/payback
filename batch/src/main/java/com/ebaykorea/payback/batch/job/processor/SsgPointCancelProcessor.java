package com.ebaykorea.payback.batch.job.processor;

import com.ebaykorea.payback.batch.config.properties.SsgPointAuthProperties;
import com.ebaykorea.payback.batch.domain.SsgPointCertifier;
import com.ebaykorea.payback.batch.domain.SsgPointProcessorDto;
import com.ebaykorea.payback.batch.domain.SsgPointTargetDto;
import com.ebaykorea.payback.batch.service.SsgPointBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SsgPointCancelProcessor implements ItemProcessor<SsgPointProcessorDto, SsgPointTargetDto> {

  private final SsgPointAuthProperties authProperties;
  private final SsgPointBatchService ssgPointBatchService;

  @Override
  public SsgPointTargetDto process(final SsgPointProcessorDto item) {
    final var authInfo = SsgPointCertifier.of(authProperties, item.getSiteType());
    final var result = ssgPointBatchService.cancel(item, authInfo);
    return result;
  }
}
