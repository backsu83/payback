package com.ebaykorea.payback.infrastructure.persistence.repository.auction;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.AUCTION_TENANT;
import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.PERSIST_001;
import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.PERSIST_002;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.domain.entity.event.SmileCashEvent;
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.EventRewardResultDto;
import com.ebaykorea.payback.core.dto.event.SetEventRewardRequestDto;
import com.ebaykorea.payback.core.exception.PaybackException;
import com.ebaykorea.payback.core.repository.SmileCashEventRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashReasonCodeRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashSaveQueueRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashTransactionRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveQueueEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.mapper.SmileCashSaveQueueEntityMapper;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Profile(AUCTION_TENANT)
@Service
@RequiredArgsConstructor
public class AuctionSmileCashEventRepository implements SmileCashEventRepository {

  private final SmileCashSaveQueueRepository smileCashSaveQueueRepository;
  private final SmileCashTransactionRepository smileCashTransactionRepository;
  private final SmileCashReasonCodeRepository smileCashReasonCodeRepository;

  private final SmileCashSaveQueueEntityMapper mapper;

  private static final int BIZ_TYPE = 9;
  private static final int DUPLICATED_REQUEST = -322;

  @Transactional
  @Override
  public Optional<EventRewardResultDto> save(final EventRewardRequestDto request) {
    return
        //중복 요청 체크
        smileCashSaveQueueRepository.findByBizKey(String.valueOf(request.getRequestNo())).stream()
            .filter(alreadyRequested(request.getMemberKey(), request.getEventType()))
            .findAny()
            .map(savedQueue -> mapper.map(request.getRequestNo(), DUPLICATED_REQUEST, savedQueue.getTxId()))
            .or(() -> { //중복 요청 건이 아닌 경우
              final var iacReasonCode = request.getEventType().getAuctionCode();
              return smileCashReasonCodeRepository.findById(iacReasonCode)
                  .map(reasonCode -> {
                    final var txId = smileCashTransactionRepository.getIacTxId(request.getMemberKey());
                    final var entity = mapper.map(txId, reasonCode.getIacReasonComment(), request);

                    smileCashSaveQueueRepository.save(entity);

                    return Optional.of(mapper.map(request.getRequestNo(), 0, txId));
                  })
                  .orElseThrow(() -> new PaybackException(PERSIST_001, iacReasonCode));
            });
  }

  @Transactional
  @Override
  public Optional<EventRewardResultDto> saveWithBudget(final EventRewardRequestDto request) {
    final var result = smileCashSaveQueueRepository.updateBudget(request.getBudgetNo(), request.getSaveAmount());
    if (result != 0) {
      throw new PaybackException(PERSIST_002, String.format("예산 할당 처리 실패, %d", request.getBudgetNo()));
    }
    return save(request);
  }

  private Predicate<SmileCashSaveQueueEntity> alreadyRequested(final String buyerId, final EventType eventType) {
    return entity -> entity.getBizType() == BIZ_TYPE &&
        entity.getReasonCode().equals(eventType.getAuctionCode()) &&
        entity.getMemberId().equals(buyerId);
  }

  @Override
  public void set(final Long seqNo, final SetEventRewardRequestDto request) {
    final var entity = mapper.map(seqNo, request);
    smileCashSaveQueueRepository.update(entity);
  }

  @Override
  public Optional<SmileCashEvent> find(final EventRewardRequestDto request) {
    return smileCashSaveQueueRepository.findByBizKey(String.valueOf(request.getRequestNo())).stream()
        .filter(alreadyRequested(request.getMemberKey(), request.getEventType()))
        .findAny()
        .map(mapper::map);
  }
}
