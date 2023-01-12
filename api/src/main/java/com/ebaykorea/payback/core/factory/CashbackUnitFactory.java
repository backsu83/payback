package com.ebaykorea.payback.core.factory;

import static com.ebaykorea.payback.util.PaybackDecimals.summarizing;
import static com.ebaykorea.payback.util.PaybackInstants.getDefaultEnableDate;

import com.ebaykorea.payback.core.domain.constant.CashbackType;
import com.ebaykorea.payback.core.domain.entity.cashback.member.Member;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.CashbackUnit;
import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshot;
import com.ebaykorea.payback.core.domain.entity.order.OrderUnit;
import com.ebaykorea.payback.core.domain.entity.order.OrderUnitKey;
import com.ebaykorea.payback.core.domain.entity.payment.Payment;
import com.ebaykorea.payback.core.domain.entity.reward.RewardBackendCashbackPolicy;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicies;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicy;
import com.ebaykorea.payback.core.factory.impl.ChargePayCashbackCreator;
import com.ebaykorea.payback.core.factory.impl.ClubDayCashbackCreator;
import com.ebaykorea.payback.core.factory.impl.DefaultCashbackCreator;
import com.ebaykorea.payback.core.factory.impl.ItemCashbackCreator;
import com.ebaykorea.payback.core.factory.impl.SellerCashbackCreator;
import com.ebaykorea.payback.core.factory.impl.SmilePayCashbackCreator;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashbackUnitFactory {
  private final SellerCashbackCreator sellerCashbackCreator;
  private final ItemCashbackCreator itemCashbackCreator;
  private final SmilePayCashbackCreator smilePayCashbackCreator;
  private final ChargePayCashbackCreator chargePayCashbackCreator;
  private final ClubDayCashbackCreator clubDayCashbackCreator;
  private final DefaultCashbackCreator defaultCashbackCreator;

  /**
   * 주문단위(주문번호)별 캐시백 목록을 가져옵니다
   */
  public List<CashbackUnit> createCashbackUnits(
      final Instant orderDate,
      final OrderUnitKey orderUnitKey,
      final OrderUnit orderUnit,
      final Member member,
      final Payment payment,
      final ItemSnapshot itemSnapshot,
      final BigDecimal bundleDiscountPrice,
      final RewardCashbackPolicies rewardCashbackPolicies
  ) {
    return Stream.of(
            Stream.of(createSellerCashbackUnit(orderDate, orderUnit, itemSnapshot, bundleDiscountPrice)),
            createOtherCashbackUnitStream(orderDate, orderUnitKey, orderUnit, member, payment, itemSnapshot, bundleDiscountPrice, rewardCashbackPolicies))
        .flatMap(s -> s)
        .collect(Collectors.toUnmodifiableList());
  }

  //판매자 캐시백
  CashbackUnit createSellerCashbackUnit(
      final Instant orderDate,
      final OrderUnit orderUnit,
      final ItemSnapshot itemSnapshot,
      final BigDecimal bundleDiscountPrice
  ) {
    return sellerCashbackCreator.create(
        getDefaultEnableDate(orderDate),
        itemSnapshot,
        orderUnit.orderUnitPrice(bundleDiscountPrice, itemSnapshot.getBuyerMileageRate()),
        orderUnit.orderUnitPrice(bundleDiscountPrice)
    );
  }

  Stream<CashbackUnit> createOtherCashbackUnitStream(
      final Instant orderDate,
      final OrderUnitKey orderUnitKey,
      final OrderUnit orderUnit,
      final Member member,
      final Payment payment,
      final ItemSnapshot itemSnapshot,
      final BigDecimal bundleDiscountPrice,
      final RewardCashbackPolicies rewardCashbackPolicies) {

    return rewardCashbackPolicies.findRewardCashbackPolicyMapByCashbackType(orderUnitKey.getBuyOrderNo()) //주문 단위에 해당하는 캐시백 정책만을 가져옵니다
        .entrySet()
        .stream()
        .map(entry -> {
          final var useEnableDate = rewardCashbackPolicies.toUseEnableDate(orderDate);
          final var cashbackAmount = entry.getValue().stream()
              .map(RewardCashbackPolicy::getCashbackAmount)
              .map(BigDecimal::valueOf)
              .collect(summarizing());
          final var basisAmount = orderUnit.orderUnitPrice(bundleDiscountPrice);

          final var backendCashbackPolicyMap = rewardCashbackPolicies.findRewardBackendCashbackPolicyMap(orderUnitKey.getBuyOrderNo());

          return createCashbackUnit(
              useEnableDate,
              member,
              payment,
              itemSnapshot,
              cashbackAmount,
              basisAmount,
              entry.getKey(),
              entry.getValue(),
              backendCashbackPolicyMap
          );
        });
  }

  CashbackUnit createCashbackUnit(
      final Instant useEnableDate,
      final Member member,
      final Payment payment,
      final ItemSnapshot itemSnapshot,
      final BigDecimal cashbackAmount,
      final BigDecimal basisAmount,
      final CashbackType cashbackType,
      final List<RewardCashbackPolicy> rewardCashbackPolicies,
      final Map<Long, List<RewardBackendCashbackPolicy>> backendCashbackPolicyMap) {

    switch (cashbackType) {
      case Item:
        return itemCashbackCreator.createItemCashback(
            useEnableDate,
            payment,
            itemSnapshot,
            cashbackAmount,
            basisAmount,
            rewardCashbackPolicies
        );

      case SmilePay:
        return smilePayCashbackCreator.create(
            useEnableDate,
            payment,
            itemSnapshot,
            cashbackAmount,
            basisAmount,
            rewardCashbackPolicies
        );
      case ChargePay:
        return chargePayCashbackCreator.create(
            useEnableDate,
            payment,
            itemSnapshot,
            cashbackAmount,
            basisAmount,
            rewardCashbackPolicies,
            backendCashbackPolicyMap
        );
      case ClubDay:
        return clubDayCashbackCreator.create(
            useEnableDate,
            member,
            payment,
            itemSnapshot,
            cashbackAmount,
            basisAmount,
            rewardCashbackPolicies,
            backendCashbackPolicyMap
        );
      default:
        //TODO: 기록이나 로깅이 필요할거 같음
        return defaultCashbackCreator.create(
            cashbackType,
            useEnableDate,
            itemSnapshot,
            cashbackAmount,
            basisAmount,
            rewardCashbackPolicies
        );
    }
  }
}
