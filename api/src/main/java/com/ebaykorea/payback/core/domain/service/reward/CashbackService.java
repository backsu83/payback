package com.ebaykorea.payback.core.domain.service.reward;

import com.ebaykorea.payback.port.service.reward.client.dto.CashbackRequestDto;
import com.ebaykorea.payback.port.service.reward.client.dto.CashbackResponseDto;
import com.ebaykorea.payback.core.domain.entity.reward.CashbackOrder;

/**
 * command , query에서 호출되는 비니즈스 로직 레이어
 * api 및 db 조회를 통해 서비스를 구성하는 단일 레이어
 */
public interface CashbackService {
//    CashbackOrder getCashbackOrder(CashbackOrderQuery request);
    CashbackOrder getCashbackOrderId(long buyOrderNo);
    CashbackResponseDto getCashbackReward(CashbackRequestDto request);

}
