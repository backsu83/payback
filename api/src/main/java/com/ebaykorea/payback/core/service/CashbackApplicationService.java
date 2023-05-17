package com.ebaykorea.payback.core.service;


import static com.ebaykorea.payback.core.domain.constant.ResponseMessageType.CASHBACK_CREATED;
import static com.ebaykorea.payback.core.domain.constant.ResponseMessageType.CASHBACK_DUPLICATED;
import static com.ebaykorea.payback.core.domain.constant.ResponseMessageType.CASHBACK_INVALID_TARGET;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.constant.ResponseMessageType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointState;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointStateImpl;
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
  private final SsgPointCreater ssgPointCreater;
  private final SsgPointRepository ssgPointRepository;
  private final SsgPointState ssgPointState;

  public ResponseMessageType setCashback(final String txKey, final String orderKey) {
    final var order = orderGateway.getOrder(orderKey);

    if (!order.isForCashback()) {
      return CASHBACK_INVALID_TARGET;
    }

    final var orderKeyMap = transactionGateway.getKeyMap(txKey, orderKey);

    final var cashbackAlreadySaved = payCashbackRepository.hasAlreadySaved(orderKeyMap);
    final var ssgPointsAlreadySaved = ssgPointRepository.hasAlreadySaved(orderKeyMap.getPackNo(), order.getBuyer().getBuyerNo(), OrderSiteType.Gmarket);

    if (cashbackAlreadySaved && ssgPointsAlreadySaved) {
      return CASHBACK_DUPLICATED;
    }

    final var paymentRecordFuture = paymentGateway.getPaymentRecordAsync(order.getPaySeq());
    final var itemSnapshotsFuture = orderGateway.getItemSnapshotAsync(order.findItemSnapshotKeys());
    final var memberFuture = memberService.getMemberAsync(order.getBuyer());

    final var paymentRecord = paymentRecordFuture.join();
    final var itemSnapshots = itemSnapshotsFuture.join();

    final var rewardCashbackPolicies = rewardGateway.getCashbackPolicies(order, paymentRecord, itemSnapshots.getItemSnapshotMap(), orderKeyMap.orderUnitKeyMap());

    final var member = memberFuture.join();

    if (!cashbackAlreadySaved) {
      final var payCashback = payCashbackCreator.create(orderKeyMap, order, member, paymentRecord, itemSnapshots, rewardCashbackPolicies);
      log.info("domain entity payCashback : {}", GsonUtils.toJson(payCashback));

      payCashbackRepository.save(payCashback);
    }

    if (!ssgPointsAlreadySaved) {
      final var ssgPoint = ssgPointCreater.withReadyUnits(rewardCashbackPolicies, member , order, orderKeyMap, paymentRecord, ssgPointState, OrderSiteType.Gmarket);
      log.info("domain entity ssgPoint: {}", GsonUtils.toJson(ssgPoint));

      ssgPointRepository.save(ssgPoint);
    }

    return CASHBACK_CREATED;
  }
}
