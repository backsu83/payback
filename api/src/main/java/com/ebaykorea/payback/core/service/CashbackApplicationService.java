package com.ebaykorea.payback.core.service;


import static com.ebaykorea.payback.core.domain.constant.ResponseMessageType.CASHBACK_CREATED;
import static com.ebaykorea.payback.core.domain.constant.ResponseMessageType.CASHBACK_DUPLICATED;
import static com.ebaykorea.payback.core.domain.constant.ResponseMessageType.CASHBACK_INVALID_TARGET;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.constant.ResponseMessageType;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicies;
import com.ebaykorea.payback.core.factory.PayCashbackCreator;
import com.ebaykorea.payback.core.gateway.OrderGateway;
import com.ebaykorea.payback.core.gateway.PaymentGateway;
import com.ebaykorea.payback.core.gateway.RewardGateway;
import com.ebaykorea.payback.core.gateway.TransactionGateway;
import com.ebaykorea.payback.core.repository.PayCashbackRepository;
import com.ebaykorea.payback.core.repository.SsgPointRepository;
import com.ebaykorea.payback.core.ssgpoint.SsgPointCreater;
import com.ebaykorea.payback.core.ssgpoint.state.SsgPointStateDelegate;
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

    //캐시백 중복 체크
    if (payCashbackRepository.isDuplicatedCashback(orderKeyMap)) {
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

    final var payCashback = payCashbackCreator.create(orderKeyMap, order, member, paymentRecord, itemSnapshots, rewardCashbackPolicies);
    log.info("domain entity payCashback : {}" , GsonUtils.toJson(payCashback));

    //final var pointState = ssgPointStateDelegate.find(OrderSiteType.Gmarket);
    //final var ssgPoint = ssgPointCreater.create(rewardCashbackPolicies.getSsgPointPolicyMap(), order, orderKeyMap, pointState.site(), pointState.ready());
    //log.info("domain entity ssgPoint: {}" , GsonUtils.toJsonPretty(ssgPoint));

    //payCashback 저장
    payCashbackRepository.save(payCashback);
    //ssgPointRepository.save(ssgPoint);

    return CASHBACK_CREATED;
  }
}
