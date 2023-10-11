package com.ebaykorea.payback.infrastructure.persistence.repository.auction;

import com.ebaykorea.payback.core.dto.event.MemberEventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.MemberEventRewardResultDto;
import com.ebaykorea.payback.core.repository.SmileCashEventRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashSaveQueueRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashTransactionRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveQueueEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.mapper.SmileCashSaveQueueEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Predicate;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.AUCTION_TENANT;

@Profile(AUCTION_TENANT)
@Service
@RequiredArgsConstructor
public class AuctionSmileCashEventRepository implements SmileCashEventRepository {

  private final SmileCashSaveQueueRepository smileCashSaveQueueRepository;
  private final SmileCashTransactionRepository smileCashTransactionRepository;

  private final SmileCashSaveQueueEntityMapper mapper;

  private static final String TOSS_REASON_CODE = "RM02Y";
  private static final int BIZ_TYPE = 9;

  private static final int DUPLICATED_REQUEST = -322;

  @Transactional
  @Override
  public Optional<MemberEventRewardResultDto> save(final String memberKey, final MemberEventRewardRequestDto request) {
    return
        //중복 요청 체크
        smileCashSaveQueueRepository.findByBizKey(String.valueOf(request.getRequestNo())).stream()
            .filter(alreadyRequested(memberKey))
            .findAny()
            .map(savedQueue -> mapper.map(request.getRequestNo(), DUPLICATED_REQUEST, savedQueue.getTxId()))
            .or(() -> { //중복 요청 건이 아닌 경우
              final var txId = smileCashTransactionRepository.getIacTxId(memberKey);
              final var entity = mapper.map(txId, memberKey, request);
              smileCashSaveQueueRepository.save(entity);
              return Optional.of(mapper.map(request.getRequestNo(), 0, txId));
            });
  }

  private Predicate<SmileCashSaveQueueEntity> alreadyRequested(final String memberKey) {
    return entity -> entity.getBizType() == BIZ_TYPE &&
        entity.getReasonCode().equals(TOSS_REASON_CODE) &&
        entity.getMemberId().equals(memberKey);
  }
}
