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
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.DOMAIN_ENTITY_002;
import static com.ebaykorea.payback.util.PaybackInstants.now;
import static com.ebaykorea.payback.util.PaybackStrings.isBlank;

@Component
@RequiredArgsConstructor
public class SsgPointUnitCreater {
  private final SsgPointStateDelegate ssgPointStateDelegate;

  private static final int DEFAULT_POLICY_DAY = 5;

  public List<SsgPointUnit> readyUnits(
      final Map<Long, RewardSsgPointPolicy> policies,
      final Order order,
      final KeyMap keyMap,
      final SsgPointState ssgPointState
  ) {
    return policies.entrySet().stream()
        .filter(entry -> entry.getValue().getIsSsgPoint())
        .map(entry -> {
          final var policy = entry.getValue();
          final var orderUnitKey = keyMap.findByOrderNo(entry.getKey())
              .orElseThrow(() -> new PaybackException(DOMAIN_ENTITY_002, "orderUnitKey"));
          final var orderUnit = order.findOrderUnitBy(orderUnitKey.getOrderUnitKey())
              .orElseThrow(() -> new PaybackException(DOMAIN_ENTITY_002, "orderUnit"));

          return SsgPointUnit.readyUnit(
              orderUnitKey.getContrNo(),
              orderUnit.getOrderItem().orderItemPrice(),
              policy.getPointExpectSaveAmount(), // ssg api 대체
              getScheduleDate(order.getOrderDate(), policy),
              policy.getIsSsgPoint(),
              ssgPointState,
              null,
              null);
        })
        .collect(Collectors.toUnmodifiableList());
  }

  private Instant getScheduleDate(final Instant orderDate, final RewardSsgPointPolicy ssgPointPolicy) {
    return orderDate.plus(ssgPointPolicy.findPolicyDay().orElse(DEFAULT_POLICY_DAY), ChronoUnit.DAYS);
  }

  public SsgPointUnit cancelUnit(final CancelSsgPointRequestDto request, final SsgPointTarget ssgPointTarget) {
    final var ssgPointStrategy = ssgPointStateDelegate.find(request.getSiteType());

    return SsgPointUnit.cancelUnit(
        ssgPointTarget.getOrderNo(),
        ssgPointTarget.getPayAmount(),
        ssgPointTarget.getSaveAmount(),
        now(), //취소는 현재날짜 (yyyy-mm-dd)
        ssgPointTarget.getAccountDate(),
        ssgPointTarget.getPointToken(),
        true,
        ssgPointStrategy,
        SsgPointOrigin.builder()
            .orgApproveId(ssgPointTarget.getPntApprId())
            .orgReceiptNo(ssgPointTarget.getReceiptNo())
            .build(),
        isBlank(request.getAdminId()) ? "payback" : request.getAdminId()
    );
  }

  public SsgPointUnit withholdUnit(final CancelSsgPointRequestDto request, final SsgPointTarget ssgPointTarget) {
    final var ssgPointStrategy = ssgPointStateDelegate.find(request.getSiteType());

    return SsgPointUnit.withholdUnit(
        ssgPointTarget.getOrderNo(),
        ssgPointTarget.getPayAmount(),
        ssgPointTarget.getSaveAmount(),
        now(), //보류는 현재날짜 (yyyy-mm-dd)
        true,
        ssgPointStrategy,
        null,
        isBlank(request.getAdminId()) ? "payback" : request.getAdminId()
    );
  }
}
