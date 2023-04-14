package com.ebaykorea.payback.core.factory.ssgpoint;

import com.ebaykorea.payback.core.domain.entity.order.KeyMap;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.reward.RewardSsgPointPolicy;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointOrigin;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointState;
import com.ebaykorea.payback.core.dto.ssgpoint.CancelSsgPointRequestDto;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointTarget;
import com.ebaykorea.payback.core.exception.PaybackException;
import com.ebaykorea.payback.core.service.SsgPointStateDelegate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.DOMAIN_ENTITY_002;
import static com.ebaykorea.payback.util.PaybackDateTimes.DATE_TIME_FORMATTER;
import static com.ebaykorea.payback.util.PaybackInstants.now;

@Component
@RequiredArgsConstructor
public class SsgPointUnitCreater {
  private final SsgPointStateDelegate ssgPointStateDelegate;

  public List<SsgPointUnit> createReadyUnits(
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

  public SsgPointUnit createCancelUnit(final CancelSsgPointRequestDto request, final SsgPointTarget ssgPointTarget) {
    final var ssgPointStrategy = ssgPointStateDelegate.find(request.getSiteType());

    return SsgPointUnit.cancelUnit(
        ssgPointTarget.getOrderNo(),
        ssgPointTarget.getPayAmount(),
        ssgPointTarget.getSaveAmount(),
        now(), //취소는 현재날짜 (yyyy-mm-dd)
        true,
        ssgPointStrategy,
        SsgPointOrigin.builder()
            .orgApproveId(ssgPointTarget.getPntApprId())
            .orgReceiptNo(ssgPointTarget.getReceiptNo())
            .build(),
        request.getAdminId()
    );
  }

  public SsgPointUnit createWithholdUnit(final CancelSsgPointRequestDto request, final SsgPointTarget ssgPointTarget) {
    final var ssgPointStrategy = ssgPointStateDelegate.find(request.getSiteType());

    return SsgPointUnit.withholdUnit(
        ssgPointTarget.getOrderNo(),
        ssgPointTarget.getPayAmount(),
        ssgPointTarget.getSaveAmount(),
        now(), //보류는 현재날짜 (yyyy-mm-dd)
        true,
        ssgPointStrategy,
        null,
        request.getAdminId()
    );
  }
}
