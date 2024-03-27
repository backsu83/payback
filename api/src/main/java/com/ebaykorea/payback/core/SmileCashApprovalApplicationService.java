package com.ebaykorea.payback.core;

import com.ebaykorea.payback.core.dto.event.ApprovalEventRewardRequestDto;
import com.ebaykorea.payback.core.gateway.UserGateway;
import com.ebaykorea.payback.core.repository.SmileCashEventApproveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmileCashApprovalApplicationService {
  private final SmileCashEventApproveRepository approveRepository;

  public void approveEventSmileCash(final Long savingNo, final ApprovalEventRewardRequestDto request) {
    //TODO
  }
}
