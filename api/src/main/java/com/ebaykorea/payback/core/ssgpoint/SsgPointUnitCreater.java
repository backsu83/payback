package com.ebaykorea.payback.core.ssgpoint;

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.DOMAIN_SSG_ENTITY_001;
import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.DOMAIN_SSG_ENTITY_002;
import static com.ebaykorea.payback.util.PaybackInstants.now;

import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import com.ebaykorea.payback.core.domain.entity.order.KeyMap;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.reward.RewardSsgPointPolicy;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointCard;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit;
import com.ebaykorea.payback.core.exception.PaybackException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class SsgPointUnitCreater {

  public List<SsgPointUnit> create(
      final List<RewardSsgPointPolicy> ssgPointPolicies,
      final Order order,
      final KeyMap keyMap,
      final SsgPointCard ssgPointCard
  ) {
    final var ssgpoint = keyMap.getOrderUnitKeys().stream()
        .map(keys -> {
          //정책서와 KeyMap 매칭 여부
          final var orderNo = keys.getBuyOrderNo();
          final var ssgPointPolicy = ssgPointPolicies.stream()
              .filter(policy->policy.getOrderNo() == keys.getBuyOrderNo())
              .findAny()
              .orElseThrow(()->new PaybackException(DOMAIN_SSG_ENTITY_001));

          //주문별 금액
          final var payAmout = order.findOrderUnitBy(keys.getOrderUnitKey())
              .orElseThrow(()->new PaybackException(DOMAIN_SSG_ENTITY_002))
              .getOrderItem()
              .orderItemPrice();

          return SsgPointUnit.of(orderNo,
              PointStatusType.Ready,
              PointTradeType.Save,
              payAmout,
              payAmout, // ssg api
              ssgPointCard.getCardNo(), // smile club api
              now().toString() , // ssgPointPolicy.getExpectSaveDate()
              null,
              ssgPointPolicy.getIsSsgPoint());
        }).collect(Collectors.toList());
    return ssgpoint;
  }
}
