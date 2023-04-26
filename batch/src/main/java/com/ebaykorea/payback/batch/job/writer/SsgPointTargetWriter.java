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
public class SsgPointTargetWriter implements ItemWriter<SsgPointTargetDto> {

  private final SsgPointTargetRepositorySupport ssgPointTargetRepositorySupport;

  @Override
  public void write(final List<? extends SsgPointTargetDto> items) {
    log.info("===== start writer =====");
    for (SsgPointTargetDto item : items) {
      log.info("itemWriter item  : {}" + GsonUtils.toJsonPretty(item));
      updateWriterSuceess(item);
    }
  }

  @Transactional
  public long updateWriterSuceess(final SsgPointTargetDto item) {
    if(item.getResponseCode().equals("PRC4081") && item.getStatus() != PointStatusType.Ready) {
      //TODO 중복처리 responseMsg 파싱 필요
      return 1L;
    }
    return ssgPointTargetRepositorySupport.updatePointTarget(item , PointStatusType.Ready.getCode()
    );
  }
}
