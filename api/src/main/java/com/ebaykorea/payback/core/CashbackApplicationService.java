package com.ebaykorea.payback.core;

import com.ebaykorea.payback.core.gateway.OrderGateway;
import com.ebaykorea.payback.core.gateway.PaymentGateway;
import com.ebaykorea.payback.core.gateway.RewardGateway;
import com.ebaykorea.payback.core.gateway.TransactionGateway;
import com.ebaykorea.payback.core.repository.CashbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashbackApplicationService {
  private final OrderGateway orderGateway;
  private final PaymentGateway paymentGateway;
  private final TransactionGateway transactionGateway;
  private final RewardGateway rewardGateway;

  private final CashbackRepository cashbackRepository;

  public void setCashback(final String txKey, final String orderKey) {
    //주문 정보
    final var order = orderGateway.getOrder(orderKey);
    if (!order.isForCashback()) {
      //TODO: 뭔가 기록을 하거나 void가 아닌 리턴값등으로 구분이 되어야 할거같다
      return;
    }

    //주문 키 매핑 정보
    final var orderKeyMap = transactionGateway.getKeyMap(txKey, orderKey);
    //상품 스냅샷 정보
    final var itemSnapshot = orderGateway.getItemSnapshot(order.findItemSnapshotKeys());
    //결제 정보
    final var paymentRecord = paymentGateway.getPaymentRecord(order.getPaySeq());

    //리워드 캐시백 정책 조회
    final var rewardCashbackPolicies = rewardGateway.getCashbackPolicies(
        order,
        paymentRecord,
        itemSnapshot.bySnapshotKey(),
        orderKeyMap.findOrderUnitKeyMap());

    //final var cashbacks = Cashbacks.of(); // TODO

    //4. cashbacks validation?

    //5. cashbacks 저장
    //cashbackRepository.save(cashbacks);
  }
}
