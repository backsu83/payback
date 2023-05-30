package com.ebaykorea.payback.batch.job.writer;

import static com.ebaykorea.payback.batch.domain.constant.ReturnMessageType.CANCEL_DUPLICATED;
import static com.ebaykorea.payback.batch.domain.constant.ReturnMessageType.EARN_DUPLICATED;
import static com.ebaykorea.payback.batch.domain.constant.ReturnMessageType.SUCCESS;
import static com.ebaykorea.payback.batch.domain.constant.ReturnMessageType.codeOf;

import com.ebaykorea.payback.batch.domain.SsgPointTargetDto;
import com.ebaykorea.payback.batch.domain.constant.PointStatusType;
import com.ebaykorea.payback.batch.domain.constant.PointTradeType;
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
    for (SsgPointTargetDto item : items) {
      updateWriterSuceess(item);
    }
  }

  @Transactional
  public long updateWriterSuceess(final SsgPointTargetDto item) {
    if(item.getTradeType() == PointTradeType.Save) {
      ssgPointTargetRepositorySupport.updatePntApprIdForCancelTradeType(item);
    }

    return ssgPointTargetRepositorySupport.updatePointTarget(item,
        item.getSaveAmount(),
        item.getPntApprId(),
        isSuccess(item.getResponseCode()),
        PointStatusType.Ready.getCode());
  }

  public boolean isSuccess(String responseCode) {
    if (codeOf(responseCode) == SUCCESS) {
      return true;
    }
    return false;
  }
}
