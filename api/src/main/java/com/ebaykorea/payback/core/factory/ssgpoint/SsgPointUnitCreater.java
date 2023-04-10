package com.ebaykorea.payback.core.factory.ssgpoint;

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.DOMAIN_ENTITY_002;
import static com.ebaykorea.payback.util.PaybackDateTimes.DATE_TIME_FORMATTER;

import com.ebaykorea.payback.core.domain.entity.order.KeyMap;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.reward.RewardSsgPointPolicy;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointStatus;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit;
import com.ebaykorea.payback.core.exception.PaybackException;
import java.time.Instant;
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

          final var orderUnit = order.findOrderUnitBy(entry.getOrderUnitKey())
              .orElseThrow(() -> new PaybackException(DOMAIN_ENTITY_002, "orderUnit"));

          if(policies.containsKey(orderUnitKey.getBuyOrderNo())) {
            final var policy = policies.get(orderUnitKey.getBuyOrderNo());
            return SsgPointUnit.of(orderUnitKey.getBuyOrderNo(),
                orderUnit.getOrderItem().orderItemPrice(),
                policy.getPointExpectSaveAmount(), // ssg api 대체
                DATE_TIME_FORMATTER.parse(policy.getExpectSaveDate() , Instant::from),
                policy.getIsSsgPoint(),
                ssgPointStatus,
                null,
                    null);
          }
          return SsgPointUnit.EMPTY;
        })
        .collect(Collectors.toUnmodifiableList());
    return ssgpoint;
  }
}
