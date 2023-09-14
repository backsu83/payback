package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.dto.member.MemberCashbackRequestDto;
import com.ebaykorea.payback.core.dto.member.MemberCashbackResultDto;

import java.util.List;

public interface SmileCashEventRepository {
  MemberCashbackResultDto save(String memberKey, List<MemberCashbackRequestDto> requests);
}
