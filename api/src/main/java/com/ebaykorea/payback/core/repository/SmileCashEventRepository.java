package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.dto.event.MemberEventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.MemberEventRewardResultDto;

import java.util.List;

public interface SmileCashEventRepository {
  List<MemberEventRewardResultDto> save(String memberKey, List<MemberEventRewardRequestDto> requests);
}
