package com.ebaykorea.payback.batch.job.writer;

import com.ebaykorea.payback.batch.domain.SsgPointProcesserDto;
import com.ebaykorea.payback.batch.domain.SsgPointTargetDto;
import com.ebaykorea.payback.batch.service.SsgPointBatchService;
import com.ebaykorea.payback.batch.util.support.GsonUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SsgPointTargetWriter implements ItemWriter<SsgPointTargetDto> {

  private final SsgPointBatchService ssgPointBatchService;

  @Override
  public void write(final List<? extends SsgPointTargetDto> items) {
    System.out.println("===== start writer =====");
    for (SsgPointTargetDto item : items) {
      System.out.println("SsgPointTargetWriter : " + GsonUtils.toJsonPretty(item));
      ssgPointBatchService.updateWriterSuceess(item);
    }
    //업데이트 된 내용 저장
  }
}
