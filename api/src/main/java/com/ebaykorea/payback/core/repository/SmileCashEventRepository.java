package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.entity.event.SmileCashEvent;
import com.ebaykorea.payback.core.dto.event.MemberEventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.MemberEventRewardResultDto;
import com.ebaykorea.payback.core.dto.event.SetEventRewardRequestDto;

import java.util.Optional;

public interface SmileCashEventRepository {
  Optional<MemberEventRewardResultDto> save(String memberKey, MemberEventRewardRequestDto request);
  void set(Long smilePayNo, SetEventRewardRequestDto request);

  Optional<SmileCashEvent> find(String memberKey, MemberEventRewardRequestDto request);
}
