package com.ebaykorea.payback.infrastructure.persistence.repository.auction;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.AUCTION_TENANT;
import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.PERSIST_001;
import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.PERSIST_002;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.domain.entity.event.SmileCashEvent;
import com.ebaykorea.payback.core.domain.entity.event.SmileCashEventResult;
import com.ebaykorea.payback.api.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.EventRewardResultDto;
import com.ebaykorea.payback.core.dto.event.SetEventRewardRequestDto;
import com.ebaykorea.payback.core.exception.PaybackException;
import com.ebaykorea.payback.core.repository.SmileCashEventRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashReasonCodeRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashSaveQueueRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashTransactionRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashReasonCodeEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveQueueEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.mapper.SmileCashSaveQueueEntityMapper;
import com.ebaykorea.payback.util.PaybackStrings;
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
  public Optional<EventRewardResultDto> save(final SmileCashEvent smileCashEvent) {
    return
        //중복 요청 체크
        smileCashSaveQueueRepository.findByBizKey(String.valueOf(smileCashEvent.getRequestNo())).stream()
            .filter(alreadyRequested(smileCashEvent.getMemberKey(), smileCashEvent.getEventType()))
            .findAny()
            .map(savedQueue -> mapper.map(smileCashEvent.getRequestNo(), DUPLICATED_REQUEST, savedQueue.getTxId()))
            .or(() -> { //중복 요청 건이 아닌 경우

              // 적립 문구가 넘어온게 없다면 코드에 매핑된 이름을 전달
              final var comment = Optional.ofNullable(smileCashEvent.getComments())
                  .filter(PaybackStrings::isNotBlank)
                  .orElseGet(() -> smileCashReasonCodeRepository.findById(smileCashEvent.getEventType().getAuctionCode())
                      .map(SmileCashReasonCodeEntity::getIacReasonComment)
                      .orElseThrow(() -> new PaybackException(PERSIST_001, smileCashEvent.getEventType().getAuctionCode())));

              final var txId = smileCashTransactionRepository.getIacTxId(smileCashEvent.getMemberKey());
              final var entity = mapper.map(txId, comment, smileCashEvent);

              smileCashSaveQueueRepository.save(entity);

              return Optional.of(mapper.map(smileCashEvent.getRequestNo(), 0, txId));
            });
  }

  @Transactional
  @Override
  public Optional<EventRewardResultDto> saveWithBudget(final SmileCashEvent smileCashEvent) {
    final var isBudgetDeducted = Optional.of(smileCashEvent.getBudgetNo())
        .filter(budgetNo -> budgetNo > 0L)
        .map(budgetNo -> smileCashSaveQueueRepository.updateBudget(budgetNo, smileCashEvent.getSaveAmount()))
        .map(res -> res == 0)
        .orElse(true);
    if (!isBudgetDeducted) {
      throw new PaybackException(PERSIST_002, String.format("예산 할당 처리 실패, %d", smileCashEvent.getBudgetNo()));
    }
    return save(smileCashEvent);
  }

  private Predicate<SmileCashSaveQueueEntity> alreadyRequested(final String buyerId, final EventType eventType) {
    return entity -> entity.getBizType() == BIZ_TYPE &&
        entity.getReasonCode().equals(eventType.getAuctionCode()) &&
        entity.getMemberId().equals(buyerId);
  }

  @Override
  public Optional<SmileCashEventResult> find(final SmileCashEvent smileCashEvent) {
    return smileCashSaveQueueRepository.findByBizKey(String.valueOf(smileCashEvent.getRequestNo())).stream()
        .filter(alreadyRequested(smileCashEvent.getMemberKey(), smileCashEvent.getEventType()))
        .findAny()
        .map(mapper::map);
  }
}
