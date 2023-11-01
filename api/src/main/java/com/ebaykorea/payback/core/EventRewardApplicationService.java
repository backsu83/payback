package com.ebaykorea.payback.core;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.domain.entity.event.SmileCashEvent;
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.EventRewardResponseDto;
import com.ebaykorea.payback.core.dto.event.MemberEventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.MemberEventRewardResultDto;
import com.ebaykorea.payback.core.gateway.UserGateway;
import com.ebaykorea.payback.core.repository.EventRewardRepository;
import com.ebaykorea.payback.core.repository.SmileCashEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

import static com.ebaykorea.payback.core.domain.constant.EventRequestStatusType.getStatusBySmilePayNo;
import static com.ebaykorea.payback.util.PaybackStrings.EMPTY;
import static com.ebaykorea.payback.util.PaybackStrings.isBlank;

@Service
@RequiredArgsConstructor
public class EventRewardApplicationService {
  private final EventRewardRepository eventRewardRepository;
  private final SmileCashEventRepository smileCashEventRepository;
  private final UserGateway userGateway;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final String DUPLICATED = "ALREADY_PROCESSED";
  private static final String NOT_FOUND = "NOT_FOUND";

  public EventRewardResponseDto saveEventReward(final EventRewardRequestDto request) {

    return eventRewardRepository.findEventReward(request)
        .map(eventReward -> {
          //중복 요청 된 경우
          final var userId = userGateway.getUserId(request.getUserToken());
          final var memberEventRewardRequest = buildMemberEventRequest(eventReward.getRequestNo(), request.getEventType(), request.getSaveAmount());

          return smileCashEventRepository.find(userId, memberEventRewardRequest)
              .map(smileCashEvent -> buildResponse(smileCashEvent.getSmilePayNo(), DUPLICATED))
              .orElse(buildResponse(EMPTY, DUPLICATED));
        })
        .or(() -> {
          //중복 요청이 아닌경우 요청 정보 저장 후 적립 요청
          final var requestNo = eventRewardRepository.save(request);

          final var userId = userGateway.getUserId(request.getUserToken());
          final var memberEventRewardRequest = buildMemberEventRequest(requestNo, request.getEventType(), request.getSaveAmount());

          return smileCashEventRepository.save(userId, memberEventRewardRequest)
              .map(this::getSmilePayNo)
              .map(smilePayNo -> {
                //적립 요청 상태 저장
                eventRewardRepository.saveStatus(requestNo, getStatusBySmilePayNo(smilePayNo));

                final var resultCode = isBlank(smilePayNo) ? FAILED : SUCCESS;
                return buildResponse(smilePayNo, resultCode);
              });
        }).orElse(buildResponse(EMPTY, FAILED));
  }

  public EventRewardResponseDto getEventReward(final EventRewardRequestDto request) {
    return eventRewardRepository.findEventReward(request)
        .map(eventReward -> {
          final var userId = userGateway.getUserId(request.getUserToken());
          final var memberEventRewardRequest = buildMemberEventRequest(eventReward.getRequestNo(), eventReward.getEventType(), BigDecimal.ZERO);

          return smileCashEventRepository.find(userId, memberEventRewardRequest)
              .map(buildResponseFromSmileCashEvent())
              .orElse(buildResponse(EMPTY, FAILED));
        })
        .orElse(buildResponse(EMPTY, NOT_FOUND));
  }

  private MemberEventRewardRequestDto buildMemberEventRequest(final long eventRequestNo, final EventType eventType, final BigDecimal saveAmount) {
    return MemberEventRewardRequestDto.builder()
        .requestNo(eventRequestNo)
        .eventType(eventType)
        .saveAmount(saveAmount)
        .build();
  }

  private EventRewardResponseDto buildResponse(final String smilePayNo, final String resultCode) {
    return EventRewardResponseDto.builder()
        .smilePayNo(smilePayNo)
        .resultCode(resultCode)
        .build();
  }

  private Function<SmileCashEvent, EventRewardResponseDto> buildResponseFromSmileCashEvent() {
    return smileCashEvent -> EventRewardResponseDto.builder()
        .smilePayNo(smileCashEvent.getSmilePayNo())
        .resultCode(smileCashEvent.getResultCode())
        .resultMessage(smileCashEvent.getResultMessage())
        .build();
  }

  private String getSmilePayNo(final MemberEventRewardResultDto memberEventRewardResult) {
    return Optional.ofNullable(memberEventRewardResult)
        .map(MemberEventRewardResultDto::getSmilePayNo)
        .filter(smilePayNo -> smilePayNo > 0L)
        .map(String::valueOf)
        .orElse(EMPTY);
  }
}
