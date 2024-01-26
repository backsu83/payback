package com.ebaykorea.payback.scheduler.service.smilecash;

import static com.ebaykorea.payback.scheduler.model.constant.TenantCode.AUCTION_TENANT;

import com.ebaykorea.payback.scheduler.client.dto.smilecash.SaveResultDto;
import com.ebaykorea.payback.scheduler.mapper.SmileCashSaveMapper;
import com.ebaykorea.payback.scheduler.repository.maindb2ex.SmileCashSaveQueueRepository;
import com.ebaykorea.payback.scheduler.repository.maindb2ex.entity.SmileCashSaveQueueEntity;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile(AUCTION_TENANT)
@Service
@RequiredArgsConstructor
public class SmileCashApprovalService {
  private final SmileCashSaveQueueRepository repository;
  private final SmileCashSaveMapper smileCashSaveMapper;

  private static final int MASS_SAVED = 1;

  @Transactional
  public void setApproved(final SaveResultDto response, final String smileUserKey, final SmileCashSaveQueueEntity entity) {
    repository.saveApproval(smileCashSaveMapper.map(response, smileUserKey, entity));
    repository.update(entity.getSeqNo(), MASS_SAVED, entity.getRetryCount(), entity.getInsertOperator());
  }
}
