package com.ebaykorea.payback.batch.job.writer;

import com.ebaykorea.payback.batch.domain.SsgPointTargetDto;
import com.ebaykorea.payback.batch.service.SsgPointBatchService;
import com.ebaykorea.payback.batch.util.support.GsonUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SsgPointTargetRecoverWriter implements ItemWriter<SsgPointTargetDto> {

  private final SsgPointBatchService ssgPointBatchService;

  @Override
  public void write(final List<? extends SsgPointTargetDto> items) throws Exception {
    System.out.println("===== start recover writer =====");
    for (SsgPointTargetDto item : items) {
      log.info("recover itemWriter item  : {}" + GsonUtils.toJsonPretty(item));
      ssgPointBatchService.updateWriterRecoverSuceess(item);
    }
  }
}
