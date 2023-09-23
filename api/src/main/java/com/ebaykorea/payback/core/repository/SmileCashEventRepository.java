package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.dto.event.MemberCashbackRequestDto;
import com.ebaykorea.payback.core.dto.event.MemberCashbackResultDto;

import java.util.List;

public interface SmileCashEventRepository {
  List<MemberCashbackResultDto> save(String memberKey, List<MemberCashbackRequestDto> requests);
}
