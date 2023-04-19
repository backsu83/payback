package com.ebaykorea.payback.batch.job.writer;

import com.ebaykorea.payback.batch.domain.SsgPointTargetDto;
import com.ebaykorea.payback.batch.domain.constant.PointStatusType;
import com.ebaykorea.payback.batch.repository.opayreward.SsgPointTargetRepositorySupport;
import com.ebaykorea.payback.batch.util.support.GsonUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class SsgPointTargetRecoverWriter implements ItemWriter<SsgPointTargetDto> {

  private final SsgPointTargetRepositorySupport ssgPointTargetRepositorySupport;

  @Override
  public void write(final List<? extends SsgPointTargetDto> items) {
    log.info("===== start recover writer =====");
    for (SsgPointTargetDto item : items) {
      log.info("recover itemWriter item  : {}" + GsonUtils.toJsonPretty(item));
      updateWriterRecoverSuceess(item);
    }
  }

  @Transactional
  public long updateWriterRecoverSuceess(final SsgPointTargetDto item) {
    return ssgPointTargetRepositorySupport.updatePointTarget(item , PointStatusType.Fail.getCode()
    );
  }
}
