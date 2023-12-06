package com.ebaykorea.payback.core;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.domain.entity.event.SmileCashEvent;
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.toss.TossEventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.toss.TossEventRewardResponseDto;
import com.ebaykorea.payback.core.dto.event.EventRewardResultDto;
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
public class TossEventRewardApplicationService {
  private final EventRewardRepository eventRewardRepository;
  private final SmileCashEventRepository smileCashEventRepository;
  private final UserGateway userGateway;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final String DUPLICATED = "ALREADY_PROCESSED";
  private static final String NOT_FOUND = "NOT_FOUND";

  public TossEventRewardResponseDto saveEventReward(final TossEventRewardRequestDto request) {

    return eventRewardRepository.findEventReward(request)
        .map(eventReward -> {
          //중복 요청 된 경우
          final var userId = userGateway.getUserId(request.getUserToken());
          final var memberEventRewardRequest = buildEventRewardRequest(eventReward.getRequestNo(), userId, request.getEventType(), request.getSaveAmount());

          return smileCashEventRepository.find(memberEventRewardRequest)
              .map(smileCashEvent -> buildResponse(smileCashEvent.getSmilePayNo(), DUPLICATED))
              .orElse(buildResponse(EMPTY, DUPLICATED));
        })
        .or(() -> {
          //중복 요청이 아닌경우 요청 정보 저장 후 적립 요청
          final var requestNo = eventRewardRepository.save(request);

          final var userId = userGateway.getUserId(request.getUserToken());
          final var eventRewardRequest = buildEventRewardRequest(requestNo, userId, request.getEventType(), request.getSaveAmount());

          return smileCashEventRepository.save(eventRewardRequest)
              .map(this::getSmilePayNo)
              .map(smilePayNo -> {
                //적립 요청 상태 저장
                eventRewardRepository.saveStatus(requestNo, getStatusBySmilePayNo(smilePayNo));

                final var resultCode = isBlank(smilePayNo) ? FAILED : SUCCESS;
                return buildResponse(smilePayNo, resultCode);
              });
        }).orElse(buildResponse(EMPTY, FAILED));
  }

  public TossEventRewardResponseDto getEventReward(final TossEventRewardRequestDto request) {
    return eventRewardRepository.findEventReward(request)
        .map(eventReward -> {
          final var userId = userGateway.getUserId(request.getUserToken());
          final var eventRewardRequest = buildEventRewardRequest(eventReward.getRequestNo(), userId, eventReward.getEventType(), BigDecimal.ZERO);

          return smileCashEventRepository.find(eventRewardRequest)
              .map(buildResponseFromSmileCashEvent())
              .orElse(buildResponse(EMPTY, FAILED));
        })
        .orElse(buildResponse(EMPTY, NOT_FOUND));
  }

  private EventRewardRequestDto buildEventRewardRequest(final long eventRequestNo, final String memberKey, final EventType eventType, final BigDecimal saveAmount) {
    return EventRewardRequestDto.builder()
        .requestNo(eventRequestNo)
        .memberKey(memberKey)
        .eventType(eventType)
        .saveAmount(saveAmount)
        .comment("토스-신세계 유니버스 클럽 가입")
        .build();
  }

  private TossEventRewardResponseDto buildResponse(final String smilePayNo, final String resultCode) {
    return TossEventRewardResponseDto.builder()
        .smilePayNo(smilePayNo)
        .resultCode(resultCode)
        .build();
  }

  private Function<SmileCashEvent, TossEventRewardResponseDto> buildResponseFromSmileCashEvent() {
    return smileCashEvent -> TossEventRewardResponseDto.builder()
        .smilePayNo(smileCashEvent.getSmilePayNo())
        .resultCode(smileCashEvent.getResultCode())
        .resultMessage(smileCashEvent.getResultMessage())
        .build();
  }

  private String getSmilePayNo(final EventRewardResultDto eventRewardResult) {
    return Optional.ofNullable(eventRewardResult)
        .map(EventRewardResultDto::getSmilePayNo)
        .filter(smilePayNo -> smilePayNo > 0L)
        .map(String::valueOf)
        .orElse(EMPTY);
  }
}
