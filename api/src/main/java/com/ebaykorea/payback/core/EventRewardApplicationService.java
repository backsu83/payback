package com.ebaykorea.payback.core;

import com.ebaykorea.payback.core.domain.constant.EventRequestStatusType;
import com.ebaykorea.payback.core.dto.event.*;
import com.ebaykorea.payback.core.gateway.RewardGateway;
import com.ebaykorea.payback.core.repository.EventRewardRepository;
import com.ebaykorea.payback.core.repository.SmileCashEventRepository;
import com.ebaykorea.payback.util.PaybackStrings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.ebaykorea.payback.util.PaybackCollections.orEmptyStream;
import static com.ebaykorea.payback.util.PaybackStrings.isBlank;

@Service
@RequiredArgsConstructor
public class EventRewardApplicationService {
  private final EventRewardRepository eventRewardRepository;
  private final SmileCashEventRepository smileCashEventRepository;
  private final RewardGateway rewardGateway;

  private static final String SMILE_CASH = "SMILE_CASH";

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final String DUPLICATED = "ALREADY_PROCESSED";


  public EventRewardResponseDto saveEventReward(final EventRewardRequestDto request) {
    final var alreadySaved = eventRewardRepository.alreadySaved(request.getRequestId(), request.getEventType());
    if (alreadySaved) {
      return build(request.getRequestId(), PaybackStrings.EMPTY, DUPLICATED);
    }
    // 요청 내역 저장 및 적립 요청 번호 채번
    final var eventRequestNo = eventRewardRepository.save(request);

    // memberApi 호출 후 G/A 여부 및 사이트 아이디 조회
    //TODO

    final var memberEventRewardRequest = buildMemberEventRequest(eventRequestNo, request);

    final var gmarket = true;
    if(!gmarket) {
      // 지마켓 아이디 일 경우 적립 호출
      return smileCashEventRepository.save("gmarketMemberKey", List.of(memberEventRewardRequest)).stream()
          .findAny()
          .map(this::getSaveProcessId)
          .map(saveProcessId -> {
            //결과 저장
            eventRewardRepository.saveStatus(request.getRequestId(), request.getEventType(), EventRequestStatusType.getStatusBySaveProcessId(saveProcessId));

            final var resultCode = isBlank(saveProcessId) ? FAILED : SUCCESS;
            return build(request.getRequestId(), saveProcessId, resultCode);
          })
          .orElse(build(request.getRequestId(), PaybackStrings.EMPTY, FAILED));
    } else {
      // 옥션 아이디일 경우 옥션 적립 호출
      return rewardGateway.saveEventCashback("auctionMemberKey", List.of(memberEventRewardRequest))
          .map(this::getSaveProcessId)
          .map(saveProcessId -> {
            //결과 저장
            eventRewardRepository.saveStatus(request.getRequestId(), request.getEventType(), EventRequestStatusType.getStatusBySaveProcessId(saveProcessId));

            final var resultCode = isBlank(saveProcessId) ? FAILED : SUCCESS;
            return build(request.getRequestId(), saveProcessId, resultCode);
          })
          .orElse(build(request.getRequestId(), PaybackStrings.EMPTY, FAILED));
    }
  }

  private MemberEventRewardRequestDto buildMemberEventRequest(final long eventRequestNo, final EventRewardRequestDto request) {
    return MemberEventRewardRequestDto.builder()
        .requestNo(eventRequestNo)
        .eventType(request.getEventType())
        .saveAmount(request.getTotalSaveAmount())
        .build();
  }

  private EventRewardResponseDto build(final String requestId, final String saveProcessId, final String resultCode) {
    return EventRewardResponseDto.builder()
        .requestId(requestId)
        .saveProcessId(saveProcessId)
        .saveProcessType(SMILE_CASH)
        .resultCode(resultCode)
        .build();
  }

  private String getSaveProcessId(final MemberEventRewardResponseDto memberEventRewardResponse) {
    return Optional.ofNullable(memberEventRewardResponse)
        .map(result -> orEmptyStream(result.getCashbackResults()))
        .flatMap(Stream::findAny)
        .map(MemberEventRewardResultDto::getSmilePayNo)
        .map(String::valueOf)
        .orElse(PaybackStrings.EMPTY);
  }
  private String getSaveProcessId(final MemberEventRewardResultDto memberEventRewardResult) {
    return Optional.ofNullable(memberEventRewardResult)
        .map(MemberEventRewardResultDto::getSmilePayNo)
        .map(String::valueOf)
        .orElse(PaybackStrings.EMPTY);
  }
}
