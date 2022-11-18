package com.ebaykorea.payback.core;

import com.ebaykorea.payback.core.factory.PayCashbackCreator;
import com.ebaykorea.payback.core.gateway.OrderGateway;
import com.ebaykorea.payback.core.gateway.PaymentGateway;
import com.ebaykorea.payback.core.gateway.RewardGateway;
import com.ebaykorea.payback.core.gateway.TransactionGateway;
import com.ebaykorea.payback.core.repository.PayCashbackRepository;
import com.ebaykorea.payback.core.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

  public void setCashback(final String txKey, final String orderKey) {
    //주문 정보
    final var order = orderGateway.getOrder(orderKey);
    if (!order.isForCashback()) {
      //TODO: 뭔가 기록을 하거나 void가 아닌 리턴값등으로 구분이 되어야 할거같다
      return;
    }

    //TODO: 동시성 고려 (applicationService 레이어에서는 동시성 처리를 하지 않고 도메인 서비스나 gateway에서 할수있도록 고려해보자)
    //TODO: applicationService 레이어에서 gateway와 repository를 호출하는것이 맞을지 고민
    //회원 정보
    final var member = memberService.getMember(order.getBuyer());
    //주문 키 매핑 정보
    final var orderKeyMap = transactionGateway.getKeyMap(txKey, orderKey);
    //결제 정보
    final var paymentRecord = paymentGateway.getPaymentRecord(order.getPaySeq());
    //상품 스냅샷 정보
    final var itemSnapshots = orderGateway.getItemSnapshot(order.findItemSnapshotKeys());

    //TODO 판매자 캐시백은 적립해야함
    if (!paymentRecord.hasMainPaymentAmount()) {
      return;
    }


    //리워드 캐시백 정책 조회
    final var rewardCashbackPolicies = rewardGateway.getCashbackPolicies(
        order,
        paymentRecord,
        itemSnapshots.getItemSnapshotMap(),
        orderKeyMap.orderUnitKeyMap());

    final var payCashback = payCashbackCreator.create(orderKeyMap, order, member, paymentRecord, itemSnapshots, rewardCashbackPolicies);
    //payCashback validation?
    //TODO 중복체크

    //TODO smilecard api 호출
    //payCashback 저장
    payCashbackRepository.save(payCashback);
    rewardGateway.saveCardT2T3Cashback(orderKeyMap , payCashback.getSmileCardCashback());
  }
}
