package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.dto.event.MemberEventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.MemberEventRewardResultDto;

import java.util.Optional;

public interface SmileCashEventRepository {
  Optional<MemberEventRewardResultDto> save(String memberKey, MemberEventRewardRequestDto request);
}
