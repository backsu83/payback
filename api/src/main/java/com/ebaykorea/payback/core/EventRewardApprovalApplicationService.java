package com.ebaykorea.payback.core;

import com.ebaykorea.payback.core.dto.event.ApprovalEventRewardRequestDto;
import com.ebaykorea.payback.core.repository.EventRewardApproveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventRewardApprovalApplicationService {
  private final EventRewardApproveRepository approveRepository;

  public void approveEventSmileCash(final Long savingNo, final ApprovalEventRewardRequestDto request) {
    //TODO
  }
}
