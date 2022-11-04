package com.ebaykorea.payback.core.factory;

import com.ebaykorea.payback.core.domain.constant.ShopType;
import com.ebaykorea.payback.core.domain.entity.cashback.*;
import com.ebaykorea.payback.core.domain.entity.cashback.member.Member;
import com.ebaykorea.payback.core.domain.entity.order.*;
import com.ebaykorea.payback.core.domain.entity.payment.Payment;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicies;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicy;
import com.ebaykorea.payback.core.exception.PaybackException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ebaykorea.payback.core.domain.constant.CashbackType.*;
import static com.ebaykorea.payback.core.domain.constant.CashbackType.ClubDay;
import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.FACTORY_001;
import static com.ebaykorea.payback.util.PaybackInstants.getDefaultEnableDate;

@Service
@RequiredArgsConstructor
public class CashbackFactory {

  //TODO: SmileCardCashback

  public List<Cashback> createCashbacks(
      final KeyMap keyMap,
      final Order order,
      final Member member,
      final Payment payment,
      final ItemSnapshots itemSnapshots,
      final RewardCashbackPolicies rewardCashbackPolicies
  ) {
    return Stream.of(
            createSellerCashbackStream(keyMap, order, itemSnapshots),
            createOtherCashbackStream(keyMap, order, member, payment, rewardCashbackPolicies))
        .flatMap(s -> s)
        .collect(Collectors.toUnmodifiableList());
  }

  Stream<Cashback> createSellerCashbackStream(
      final KeyMap keyMap,
      final Order order,
      final ItemSnapshots itemSnapshots
  ) {
    return order.getOrderUnits().stream()
        .map(orderUnit -> {
              final var orderUnitKey = keyMap.findOrderUnitKey(orderUnit.getOrderUnitKey())
                  .orElseThrow(() -> new PaybackException(FACTORY_001, "orderUnitKey를 찾을 수 없습니다"));
              return createSellerCashback(
                  order.getOrderDate(),
                  orderUnitKey,
                  orderUnit,
                  itemSnapshots.bySnapshotKey().get(orderUnit.getOrderItem().getItemSnapshotKey()),
                  order.findBundleDiscountMap().get(orderUnit.getOrderUnitKey())
              );
            }
        );
  }

  Stream<Cashback> createOtherCashbackStream(
      final KeyMap keyMap,
      final Order order,
      final Member member,
      final Payment payment,
      final RewardCashbackPolicies rewardCashbackPolicies
  ) {
    return rewardCashbackPolicies.getCashbackPolicies().stream()
        .map(policy -> {
          final var orderUnitKey = keyMap.findOrderUnitKeyByOrderNo(policy.getPolicyKey())
              .orElseThrow(() -> new PaybackException(FACTORY_001, "orderUnitKey를 찾을 수 없습니다"));
          final var orderUnit = order.findOrderUnit(orderUnitKey.getOrderUnitKey())
              .orElseThrow(() -> new PaybackException(FACTORY_001, "orderUnit을 찾을 수 없습니다"));

          return createCashback(
              order.getOrderDate(),
              orderUnit,
              member,
              payment,
              order.findBundleDiscountMap().get(orderUnit.getOrderUnitKey()),
              rewardCashbackPolicies,
              policy
          );
        });
  }

  Cashback createSellerCashback(
      final Instant orderDate,
      final OrderUnitKey orderUnitKey,
      final OrderUnit orderUnit,
      final ItemSnapshot itemSnapshot,
      final BigDecimal bundleDiscountPrice) {
    return new SellerCashback(
        orderUnitKey.getBuyOrderNo(),
        itemSnapshot.getItemNo(),
        Seller,
        ShopType.Unknown, //TODO
        orderUnit.orderUnitPrice(bundleDiscountPrice, itemSnapshot.getBuyerMileageRate()),
        orderUnit.orderUnitPrice(bundleDiscountPrice),
        getDefaultEnableDate(orderDate)
    );
  }

  Cashback createCashback(
      final Instant orderDate,
      final OrderUnit orderUnit,
      final Member member,
      final Payment payment,
      final BigDecimal bundleDiscountPrice,
      final RewardCashbackPolicies rewardCashbackPolicies,
      final RewardCashbackPolicy rewardCashbackPolicy) {
    final var useEnableDate = rewardCashbackPolicies.toUseEnableDate(orderDate);
    final var cashbackAmount = rewardCashbackPolicies.getCashbackAmount(rewardCashbackPolicy.getPolicyKey(), rewardCashbackPolicy.getCashbackCd());
    final var basisAmount = orderUnit.orderUnitPrice(bundleDiscountPrice);

    //아이템
    if (rewardCashbackPolicy.getCashbackCd() == Item) {
      return new ItemCashback(
          rewardCashbackPolicy.getPolicyKey(),
          orderUnit.getOrderItem().getItemNo(),
          rewardCashbackPolicy.getCashbackCd(),
          ShopType.Unknown, //TODO
          cashbackAmount,
          basisAmount,
          useEnableDate,
          payment.isSmilePayPayment()
      );
    }
    //스마일페이
    else if (rewardCashbackPolicy.getCashbackCd() == SmilePay) {
      return new SmilePayCashback(
          rewardCashbackPolicy.getPolicyKey(),
          orderUnit.getOrderItem().getItemNo(),
          rewardCashbackPolicy.getCashbackCd(),
          ShopType.Unknown, //TODO
          cashbackAmount,
          basisAmount,
          useEnableDate,
          payment.isSmilePayPayment()
      );
    }
    //자동충전
    else if (rewardCashbackPolicy.getCashbackCd() == ChargePay) {
      return new ChargePayCashback(
          rewardCashbackPolicy.getPolicyKey(),
          orderUnit.getOrderItem().getItemNo(),
          rewardCashbackPolicy.getCashbackCd(),
          ShopType.Unknown, //TODO
          cashbackAmount,
          basisAmount,
          useEnableDate,
          BigDecimal.ZERO, //TODO
          payment.isChargePayment()
      );
    }
    //클럽
    else if (rewardCashbackPolicy.getCashbackCd() == ClubDay) {
      return new ClubDayCashback(
          rewardCashbackPolicy.getPolicyKey(),
          orderUnit.getOrderItem().getItemNo(),
          rewardCashbackPolicy.getCashbackCd(),
          ShopType.Unknown, //TODO
          cashbackAmount,
          basisAmount,
          useEnableDate,
          member.isSmileClubMember()
      );
    } else {
      return new NoneCashback(
          rewardCashbackPolicy.getPolicyKey(),
          orderUnit.getOrderItem().getItemNo(),
          rewardCashbackPolicy.getCashbackCd(),
          ShopType.Unknown, //TODO
          cashbackAmount,
          basisAmount,
          useEnableDate
      );
    }
  }
}
