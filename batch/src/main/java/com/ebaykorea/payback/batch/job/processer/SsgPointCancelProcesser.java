package com.ebaykorea.payback.batch.job.processer;

import com.ebaykorea.payback.batch.domain.SsgPointProcesserDto;
import com.ebaykorea.payback.batch.service.SsgPointBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SsgPointCancelProcesser implements ItemProcessor<SsgPointProcesserDto, SsgPointProcesserDto> {

  private final SsgPointBatchService ssgPointBatchService;

  @Override
  public SsgPointProcesserDto process(final SsgPointProcesserDto item) throws Exception {
    ssgPointBatchService.earn(item);
    return item;
  }
}
