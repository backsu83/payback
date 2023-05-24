package com.ebaykorea.payback.batch.job.writer;

import static com.ebaykorea.payback.batch.domain.constant.ReturnMessageType.CANCEL_DUPLICATED;
import static com.ebaykorea.payback.batch.domain.constant.ReturnMessageType.EARN_DUPLICATED;
import static com.ebaykorea.payback.batch.domain.constant.ReturnMessageType.SUCCESS;
import static com.ebaykorea.payback.batch.domain.constant.ReturnMessageType.codeOf;

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
    for (SsgPointTargetDto item : items) {
      log.info("recover itemWriter item  : {}", GsonUtils.toJson(item));
      updateWriterRecoverSuceess(item);
    }
  }

  @Transactional
  public long updateWriterRecoverSuceess(final SsgPointTargetDto item) {
    if(codeOf(item.getResponseCode()) == EARN_DUPLICATED || codeOf(item.getResponseCode()) == CANCEL_DUPLICATED) {
      if(ssgPointTargetRepositorySupport.existsPntApprId(item.getOrderNo() , item.getTradeType().getCode())) {
        return 1L;
      }
    }

    switch (codeOf(item.getResponseCode())) {
      case EARN_DUPLICATED:
      case CANCEL_DUPLICATED:
        return ssgPointTargetRepositorySupport.updatePointTarget(item ,
                item.getDupApoint(),
                item.getDupApprid(),
                isSuccess(item.getResponseCode()),
                PointStatusType.Fail.getCode());
      default:
        return ssgPointTargetRepositorySupport.updatePointTarget(item ,
                item.getSaveAmount(),
                item.getPntApprId(),
                isSuccess(item.getResponseCode()),
                PointStatusType.Fail.getCode());
    }
  }

  public boolean isSuccess(String responseCode) {
    if(codeOf(responseCode) == SUCCESS || codeOf(responseCode) == EARN_DUPLICATED || codeOf(responseCode) == CANCEL_DUPLICATED) {
      return true;
    }
    return false;
  }
}