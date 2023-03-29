package com.ebaykorea.payback.batch.job.writer;

import com.ebaykorea.payback.batch.domain.SsgPointProcesserDto;
import com.ebaykorea.payback.batch.domain.SsgPointTargetDto;
import com.ebaykorea.payback.batch.util.support.GsonUtils;
import java.util.List;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.stereotype.Component;

@Component
public class SsgPointTargetWriter implements ItemStreamWriter<SsgPointTargetDto> {

  @Override
  public void open(final ExecutionContext executionContext) throws ItemStreamException {

  }

  @Override
  public void update(final ExecutionContext executionContext) throws ItemStreamException {

  }

  @Override
  public void close() throws ItemStreamException {

  }

  @Override
  public void write(final List<? extends SsgPointTargetDto> items) throws Exception {
    System.out.println("===== start writer =====");
    for (SsgPointTargetDto entity: items) {
      System.out.println("SsgPointTargetWriter : " + GsonUtils.toJsonPretty(entity));
    }
    //업데이트 된 내용 저장
  }
}
