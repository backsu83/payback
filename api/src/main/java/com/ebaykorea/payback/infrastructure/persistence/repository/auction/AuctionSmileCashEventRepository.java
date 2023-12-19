package com.ebaykorea.payback.infrastructure.persistence.repository.auction;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.AUCTION_TENANT;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.domain.entity.event.SmileCashEvent;
import com.ebaykorea.payback.core.dto.event.CashEventRewardRequest;
import com.ebaykorea.payback.core.dto.event.CashEventRewardResult;
import com.ebaykorea.payback.core.dto.event.SetEventRewardRequestDto;
import com.ebaykorea.payback.core.repository.SmileCashEventRepository;
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

  private final SmileCashSaveQueueEntityMapper mapper;

  private static final int BIZ_TYPE = 9;
  private static final int DUPLICATED_REQUEST = -322;

  @Transactional
  @Override
  public Optional<CashEventRewardResult> save(final CashEventRewardRequest request) {
    return
        //중복 요청 체크
        smileCashSaveQueueRepository.findByBizKey(String.valueOf(request.getRequestNo())).stream()
            .filter(alreadyRequested(request.getRequestId(), request.getEventType()))
            .findAny()
            .map(savedQueue -> mapper.map(request.getRequestNo(), DUPLICATED_REQUEST, savedQueue.getTxId()))
            .or(() -> { //중복 요청 건이 아닌 경우
              final var txId = smileCashTransactionRepository.getIacTxId(request.getRequestId());
              final var entity = mapper.map(txId, request);
              smileCashSaveQueueRepository.save(entity);
              return Optional.of(mapper.map(request.getRequestNo(), 0, txId));
            });
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
  public Optional<SmileCashEvent> find(final String buyerId, final CashEventRewardRequest request) {
    return smileCashSaveQueueRepository.findByBizKey(String.valueOf(request.getRequestNo())).stream()
        .filter(alreadyRequested(buyerId, request.getEventType()))
        .findAny()
        .map(mapper::map);
  }
}
