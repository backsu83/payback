package com.ebaykorea.payback.core.factory;

import com.ebaykorea.payback.core.domain.entity.cashback.Cashback;
import com.ebaykorea.payback.core.domain.entity.cashback.member.Member;
import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshots;
import com.ebaykorea.payback.core.domain.entity.order.KeyMap;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.payment.Payment;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicies;
import com.ebaykorea.payback.core.exception.PaybackException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.FACTORY_001;

@Service
@RequiredArgsConstructor
public class CashbackCreator {

  private final CashbackUnitFactory cashbackUnitFactory;

  public List<Cashback> createCashbacks(
      final KeyMap keyMap,
      final Order order,
      final Member member,
      final Payment payment,
      final ItemSnapshots itemSnapshots,
      final RewardCashbackPolicies rewardCashbackPolicies
  ) {
    return order.getOrderUnits().stream()
        .map(orderUnit -> {
          final var orderUnitKey = keyMap.findBy(orderUnit.getOrderUnitKey())
              .orElseThrow(() -> new PaybackException(FACTORY_001, "orderUnitKey를 찾을 수 없습니다"));
          final var itemSnapshot = itemSnapshots.findBy(orderUnit.getItemSnapshotKey())
              .orElseThrow(() -> new PaybackException(FACTORY_001, "itemSnapshot을 찾을 수 없습니다"));
          final var bundleDiscountPrice = order.getBundleDiscountPrice(orderUnit.getOrderUnitKey());

          //주문단위(주문번호)별 캐시백 목록
          final var cashbackUnits = cashbackUnitFactory.createCashbackUnits(
              order.getOrderDate(),
              orderUnitKey,
              orderUnit,
              member,
              payment,
              itemSnapshot,
              bundleDiscountPrice,
              rewardCashbackPolicies);

          return Cashback.of(orderUnitKey.getOrderUnitKey(), orderUnitKey.getBuyOrderNo(), cashbackUnits);
        })
        .collect(Collectors.toUnmodifiableList());
  }
}
