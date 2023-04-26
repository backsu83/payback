package com.ebaykorea.payback.core.service;


import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.constant.ResponseMessageType;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicies;
import com.ebaykorea.payback.core.factory.cashback.PayCashbackCreator;
import com.ebaykorea.payback.core.factory.ssgpoint.SsgPointCreater;
import com.ebaykorea.payback.core.gateway.OrderGateway;
import com.ebaykorea.payback.core.gateway.PaymentGateway;
import com.ebaykorea.payback.core.gateway.RewardGateway;
import com.ebaykorea.payback.core.gateway.TransactionGateway;
import com.ebaykorea.payback.core.repository.PayCashbackRepository;
import com.ebaykorea.payback.core.repository.SsgPointRepository;
import com.ebaykorea.payback.util.support.GsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.ebaykorea.payback.core.domain.constant.ResponseMessageType.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CashbackApplicationService {
  private final OrderGateway orderGateway;
  private final PaymentGateway paymentGateway;
  private final TransactionGateway transactionGateway;
  private final RewardGateway rewardGateway;

  private final MemberService memberService;
  private final PayCashbackCreator payCashbackCreator;

  private final PayCashbackRepository payCashbackRepository;
  private final SsgPointStateDelegate ssgPointStateDelegate;
  private final SsgPointCreater ssgPointCreater;
  private final SsgPointRepository ssgPointRepository;

  public ResponseMessageType setCashback(final String txKey, final String orderKey) {
    //주문 정보
    final var order = orderGateway.getOrder(orderKey);

    if (!order.isForCashback()) {
      return CASHBACK_INVALID_TARGET;
    }

    //주문 키 매핑 정보
    final var orderKeyMap = transactionGateway.getKeyMap(txKey, orderKey);

    final var cashbackAlreadySaved = payCashbackRepository.hasAlreadySaved(orderKeyMap);
    final var ssgPointsAlreadySaved = ssgPointRepository.hasAlreadySaved(orderKeyMap.getPackNo(), order.getBuyer().getBuyerNo(), OrderSiteType.Gmarket);

    if (cashbackAlreadySaved && ssgPointsAlreadySaved) {
      return CASHBACK_DUPLICATED;
    }

    //결제 정보
    final var paymentRecordFuture = paymentGateway.getPaymentRecordAsync(order.getPaySeq());
    //상품 스냅샷 정보
    final var itemSnapshotsFuture = orderGateway.getItemSnapshotAsync(order.findItemSnapshotKeys());
    //회원 정보
    final var memberFuture = memberService.getMemberAsync(order.getBuyer());

    final var paymentRecord = paymentRecordFuture.join();
    final var itemSnapshots = itemSnapshotsFuture.join();

    //리워드 캐시백 정책 조회
    final var rewardCashbackPolicies = paymentRecord.hasMainPaymentAmount() ? //주 결제수단 금액이 있는 경우에만 정책 조회
        rewardGateway.getCashbackPolicies(order, paymentRecord, itemSnapshots.getItemSnapshotMap(), orderKeyMap.orderUnitKeyMap()) :
        RewardCashbackPolicies.EMPTY;

    final var member = memberFuture.join();

    //캐시백 중복 체크 FIXME: 임시 조정
    if (!cashbackAlreadySaved) {
      final var payCashback = payCashbackCreator.create(orderKeyMap, order, member, paymentRecord, itemSnapshots, rewardCashbackPolicies);
      log.info("domain entity payCashback : {}", GsonUtils.toJsonPretty(payCashback));

      payCashbackRepository.save(payCashback);
    }

    if (!ssgPointsAlreadySaved) {
      final var pointState = ssgPointStateDelegate.find(OrderSiteType.Gmarket);
      final var ssgPoint = ssgPointCreater.withReadyUnits(rewardCashbackPolicies, member , order, orderKeyMap, pointState);
      log.info("domain entity ssgPoint: {}", GsonUtils.toJsonPretty(ssgPoint));

      ssgPointRepository.save(ssgPoint);
    }

    return CASHBACK_CREATED;
  }
}
