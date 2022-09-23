package com.ebaykorea.payback.core.domain.query;

import com.ebaykorea.payback.core.domain.query.dto.CashbackOrderQuery;
import com.ebaykorea.payback.core.domain.query.dto.CashbackRewardQuery;
import com.ebaykorea.payback.port.service.reward.client.dto.CashbackRequestDto;

/**
 * controller 에서 요청 받은 데이터를 각각에 service에 데이터를 전달
 * service를 그룹핑을 하는 개념에 레이어 (단일 서비스도 호출될수도 있다)
 */
public interface CashbackQuery {

    CashbackRewardQuery getCashbackReward(final CashbackRequestDto request);
    CashbackOrderQuery getCashbackOrder(long buyOrderNo , CashbackRequestDto request);
}
