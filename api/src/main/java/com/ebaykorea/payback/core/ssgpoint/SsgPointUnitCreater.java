package com.ebaykorea.payback.core.ssgpoint;

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.DOMAIN_ENTITY_002;
import static com.ebaykorea.payback.util.PaybackInstants.now;

import com.ebaykorea.payback.core.domain.entity.order.KeyMap;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.reward.RewardSsgPointPolicy;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointStatus;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit;
import com.ebaykorea.payback.core.exception.PaybackException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class SsgPointUnitCreater {

  public List<SsgPointUnit> create(
      final Map<Long, RewardSsgPointPolicy> policies,
      final Order order,
      final KeyMap keyMap,
      final SsgPointStatus ssgPointStatus
  ) {
    final var ssgpoint = order.getOrderUnits().stream()
        .map(entry -> {
          final var orderUnitKey = keyMap.findBy(entry.getOrderUnitKey())
              .orElseThrow(() -> new PaybackException(DOMAIN_ENTITY_002, "orderUnitKey"));
          final var isPolicy = policies.get(orderUnitKey.getBuyOrderNo()).getIsSsgPoint();
          final var orderUnit = order.findOrderUnitBy(entry.getOrderUnitKey())
              .orElseThrow(() -> new PaybackException(DOMAIN_ENTITY_002, "orderUnit"));

          return SsgPointUnit.of(orderUnitKey.getBuyOrderNo(),
              orderUnit.getOrderItem().orderItemPrice(),
              orderUnit.getOrderItem().orderItemPrice(), // ssg api 대체
              now(), // ssgPointPolicy.getExpectSaveDate()
              isPolicy,
              ssgPointStatus,
              null);
        })
        .collect(Collectors.toUnmodifiableList());
    return ssgpoint;
  }


}
