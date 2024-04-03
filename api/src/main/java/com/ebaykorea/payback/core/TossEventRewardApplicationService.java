package com.ebaykorea.payback.core;

import static com.ebaykorea.payback.core.domain.constant.TossRewardRequestStatusType.getStatusBySmilePayNo;
import static com.ebaykorea.payback.util.PaybackStrings.EMPTY;
import static com.ebaykorea.payback.util.PaybackStrings.isBlank;

import com.ebaykorea.payback.core.dto.event.toss.TossRewardRequestDto;
import com.ebaykorea.payback.core.domain.entity.event.request.SmileCashEventResult;
import com.ebaykorea.payback.core.domain.entity.event.request.Toss;
import com.ebaykorea.payback.core.dto.event.EventRewardResultDto;
import com.ebaykorea.payback.core.dto.event.toss.TossEventRewardResponseDto;
import com.ebaykorea.payback.core.gateway.UserGateway;
import com.ebaykorea.payback.core.repository.EventRewardRequestRepository;
import com.ebaykorea.payback.core.repository.TossEventRewardRequestRepository;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TossEventRewardApplicationService {
  private final TossEventRewardRequestRepository tossRewardRepository;
  private final EventRewardRequestRepository eventRewardRequestRepository;
  private final UserGateway userGateway;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final String DUPLICATED = "ALREADY_PROCESSED";
  private static final String NOT_FOUND = "NOT_FOUND";

  public TossEventRewardResponseDto saveEventReward(final TossRewardRequestDto request) {

    return tossRewardRepository.find(request)
        .map(eventReward -> {
          //중복 요청 된 경우
          final var userId = userGateway.getUserId(request.getUserToken());

          return eventRewardRequestRepository.find(
                  Toss.of(eventReward.getRequestNo(), userId, request.getAmount()))
              .map(smileCashEvent -> buildResponse(smileCashEvent.getSmilePayNo(), DUPLICATED))
              .orElse(buildResponse(EMPTY, DUPLICATED));
        })
        .or(() -> {
          //중복 요청이 아닌경우 요청 정보 저장 후 적립 요청
          final var requestNo = tossRewardRepository.save(request);
          final var userId = userGateway.getUserId(request.getUserToken());

          return eventRewardRequestRepository.save(Toss.of(requestNo, userId, request.getAmount()))
              .map(this::getSmilePayNo)
              .map(smilePayNo -> {
                //적립 요청 상태 저장
                tossRewardRepository.saveStatus(requestNo, getStatusBySmilePayNo(smilePayNo));

                final var resultCode = isBlank(smilePayNo) ? FAILED : SUCCESS;
                return buildResponse(smilePayNo, resultCode);
              });
        }).orElse(buildResponse(EMPTY, FAILED));
  }

  public TossEventRewardResponseDto getEventReward(final TossRewardRequestDto request) {
    return tossRewardRepository.find(request)
        .map(eventReward -> {
          final var userId = userGateway.getUserId(request.getUserToken());

          return eventRewardRequestRepository.find(
                  Toss.of(eventReward.getRequestNo(), userId, BigDecimal.ZERO))
              .map(buildResponseFromSmileCashEvent())
              .orElse(buildResponse(EMPTY, FAILED));
        })
        .orElse(buildResponse(EMPTY, NOT_FOUND));
  }

  private TossEventRewardResponseDto buildResponse(final String smilePayNo, final String resultCode) {
    return TossEventRewardResponseDto.builder()
        .smilePayNo(smilePayNo)
        .resultCode(resultCode)
        .build();
  }

  private Function<SmileCashEventResult, TossEventRewardResponseDto> buildResponseFromSmileCashEvent() {
    return smileCashEvent -> TossEventRewardResponseDto.builder()
        .smilePayNo(smileCashEvent.getSmilePayNo())
        .resultCode(smileCashEvent.getResultCode())
        .resultMessage(smileCashEvent.getResultMessage())
        .build();
  }

  private String getSmilePayNo(final EventRewardResultDto eventRewardResult) {
    return Optional.ofNullable(eventRewardResult)
        .map(EventRewardResultDto::getSavingNo)
        .filter(smilePayNo -> smilePayNo > 0L)
        .map(String::valueOf)
        .orElse(EMPTY);
  }
}
