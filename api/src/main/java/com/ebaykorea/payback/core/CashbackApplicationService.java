package com.ebaykorea.payback.core;

import com.ebaykorea.payback.core.domain.model.Cashbacks;
import com.ebaykorea.payback.core.repository.CashbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashbackApplicationService {

  private final CashbackRepository cashbackRepository;

  public void setCashback(final String orderKey) {
    //1. 주문정보 조회

    //2. 리워드 정책 조회

    //3. 주문 정보와 리워드 정책 정보를 통해 캐시백 도메인 모델 생성
    //final var cashbacks = Cashbacks.of(orderKey); //임시 코드

    //4. 도메인 모델 validation?

    //5. 도메인 모델 저장
    //cashbackRepository.save(cashbacks);
  }
}
