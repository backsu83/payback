package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.entity.event.SmileCashEvent;
import com.ebaykorea.payback.core.dto.event.CashEventRewardRequest;
import com.ebaykorea.payback.core.dto.event.CashEventRewardResult;
import com.ebaykorea.payback.core.dto.event.SetEventRewardRequestDto;
import java.util.Optional;

public interface SmileCashEventRepository {
  Optional<CashEventRewardResult> save(CashEventRewardRequest reqest);
  void set(Long smilePayNo, SetEventRewardRequestDto request);

  Optional<SmileCashEvent> find(String memberKey, CashEventRewardRequest request);
}
