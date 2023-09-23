package com.ebaykorea.payback.core;

import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.core.repository.EventRewardRepository;
import com.ebaykorea.payback.core.repository.SmileCashEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventRewardApplicationService {
  private final EventRewardRepository eventRewardRepository;
  private final SmileCashEventRepository smileCashEventRepository;

  //TODO
  public void saveEventReward(final EventRewardRequestDto request) {
    // 요청 내역 저장 및 적립 요청 번호 채번
    final var eventRequestNo = eventRewardRepository.save(request);

    // memberApi 호출 후 G/A 여부 및 사이트 아이디 조회

    // 옥션 아이디일 경우 옥션 적립 호출

    // 지마켓 아이디 일 경우 적립 호출
  }
}
