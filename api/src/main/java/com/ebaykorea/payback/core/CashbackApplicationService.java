package com.ebaykorea.payback.core;


import static com.ebaykorea.payback.api.dto.common.ResponseMessageType.CASHBACK_INVALID_TARGET;

import com.ebaykorea.payback.api.dto.common.CommonResponse;
import com.ebaykorea.payback.core.domain.entity.cashback.member.Member;
import com.ebaykorea.payback.core.domain.entity.order.Buyer;
import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshots;
import com.ebaykorea.payback.core.domain.entity.order.KeyMap;
import com.ebaykorea.payback.core.domain.entity.payment.Payment;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicies;
import com.ebaykorea.payback.core.factory.PayCashbackCreator;
import com.ebaykorea.payback.core.gateway.OrderGateway;
import com.ebaykorea.payback.core.gateway.PaymentGateway;
import com.ebaykorea.payback.core.gateway.RewardGateway;
import com.ebaykorea.payback.core.gateway.TransactionGateway;
import com.ebaykorea.payback.core.repository.PayCashbackRepository;
import com.ebaykorea.payback.core.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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

  public CommonResponse setCashback(final String txKey, final String orderKey) {
    //주문 정보
    final var order = orderGateway.getOrder(orderKey);
    if (!order.isForCashback()) {
      //TODO: 뭔가 기록을 하거나 void가 아닌 리턴값등으로 구분이 되어야 할거같다
      return CommonResponse.success(CASHBACK_INVALID_TARGET);
    }

    //주문 키 매핑 정보
    final var orderKeyMapFuture = getKeyMapAsync(txKey, orderKey);
    //결제 정보
    final var paymentRecordFuture = getPaymentRecordAsync(order.getPaySeq());
    //상품 스냅샷 정보
    final var itemSnapshotsFuture = getItemSnapshotAsync(order.findItemSnapshotKeys());
    //회원 정보
    final var memberFuture = getMemberAsync(order.getBuyer());

    final var orderKeyMap = orderKeyMapFuture.join();
    final var paymentRecord = paymentRecordFuture.join();
    final var itemSnapshots = itemSnapshotsFuture.join();

    //리워드 캐시백 정책 조회
    final var rewardCashbackPolicies = paymentRecord.hasMainPaymentAmount() ? //주 결제수단 금액이 있는 경우에만 정책 조회
        rewardGateway.getCashbackPolicies(order, paymentRecord, itemSnapshots.getItemSnapshotMap(), orderKeyMap.orderUnitKeyMap()) :
        RewardCashbackPolicies.EMPTY;

    final var member = memberFuture.join();

    final var payCashback = payCashbackCreator.create(orderKeyMap, order, member, paymentRecord, itemSnapshots, rewardCashbackPolicies);
    //payCashback validation?
    //TODO 중복체크

    //payCashback 저장
    payCashbackRepository.save(payCashback);
    return CommonResponse.create();
  }

  private CompletableFuture<KeyMap> getKeyMapAsync(final String txKey, final String orderKey) {
    return CompletableFuture.supplyAsync(() -> transactionGateway.getKeyMap(txKey, orderKey));
  }
  private CompletableFuture<Payment> getPaymentRecordAsync(final Long paySeq) {
    return CompletableFuture.supplyAsync(() -> paymentGateway.getPaymentRecord(paySeq));
  }
  private CompletableFuture<ItemSnapshots> getItemSnapshotAsync(final List<String> itemSnapshotKeys) {
    return CompletableFuture.supplyAsync(() -> orderGateway.getItemSnapshot(itemSnapshotKeys));
  }
  private CompletableFuture<Member> getMemberAsync(final Buyer buyer) {
    return CompletableFuture.supplyAsync(() -> memberService.getMember(buyer));
  }
}
