package com.ebaykorea.payback.batch.job.processer;

import com.ebaykorea.payback.batch.domain.SsgPointProcesserDto;
import com.ebaykorea.payback.batch.service.SsgPointBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SsgPointEarnProcesser implements ItemProcessor<SsgPointProcesserDto, SsgPointProcesserDto> {

  private final SsgPointBatchService ssgPointBatchService;

  @Override
  public SsgPointProcesserDto process(final SsgPointProcesserDto item) {

    ssgPointBatchService.earn(item);
    return item;
  }
}
