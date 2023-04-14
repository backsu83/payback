package com.ebaykorea.payback.core.factory.ssgpoint;

import com.ebaykorea.payback.core.domain.entity.order.KeyMap;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.reward.RewardSsgPointPolicy;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointState;
import com.ebaykorea.payback.core.exception.PaybackException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.DOMAIN_ENTITY_002;
import static com.ebaykorea.payback.util.PaybackDateTimes.DATE_TIME_FORMATTER;

@Component
public class SsgPointUnitCreater {

  public List<SsgPointUnit> create(
      final Map<Long, RewardSsgPointPolicy> policies,
      final Order order,
      final KeyMap keyMap,
      final SsgPointState ssgPointState
  ) {
    return order.getOrderUnits().stream()
        .map(entry -> {
          final var orderUnitKey = keyMap.findBy(entry.getOrderUnitKey())
              .orElseThrow(() -> new PaybackException(DOMAIN_ENTITY_002, "orderUnitKey"));

          final var orderUnit = order.findOrderUnitBy(entry.getOrderUnitKey())
              .orElseThrow(() -> new PaybackException(DOMAIN_ENTITY_002, "orderUnit"));

          if (policies.containsKey(orderUnitKey.getBuyOrderNo())) {
            final var policy = policies.get(orderUnitKey.getBuyOrderNo());
            return SsgPointUnit.readyUnit(orderUnitKey.getBuyOrderNo(),
                orderUnit.getOrderItem().orderItemPrice(),
                policy.getPointExpectSaveAmount(), // ssg api 대체
                DATE_TIME_FORMATTER.parse(policy.getExpectSaveDate(), Instant::from),
                policy.getIsSsgPoint(),
                ssgPointState,
                null,
                null);
          }
          return SsgPointUnit.EMPTY;
        })
        .collect(Collectors.toUnmodifiableList());
  }
}
