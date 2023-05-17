package com.ebaykorea.payback.core.factory.ssgpoint;

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.DOMAIN_ENTITY_002;
import static com.ebaykorea.payback.util.PaybackInstants.now;

import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.ebaykorea.payback.core.domain.entity.order.KeyMap;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.payment.Payment;
import com.ebaykorea.payback.core.domain.entity.reward.RewardSsgPointPolicy;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointOrigin;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointState;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointTarget;
import com.ebaykorea.payback.core.exception.PaybackException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SsgPointUnitCreater {

  private static final int DEFAULT_POLICY_DAY = 5;

  public List<SsgPointUnit> readyUnits(
      final Map<Long, RewardSsgPointPolicy> policies,
      final Order order,
      final KeyMap keyMap,
      final Payment payment,
      final SsgPointState ssgPointState
  ) {
    return policies.entrySet().stream()
        .filter(entry -> canSave(entry.getValue().getIsSsgPoint(), payment.isSmilePayPayment()))
        .map(entry -> {
          final var policy = entry.getValue();
          final var orderUnitKey = keyMap.findByOrderNo(entry.getKey())
              .orElseThrow(() -> new PaybackException(DOMAIN_ENTITY_002, "orderUnitKey"));
          final var orderUnit = order.findOrderUnitBy(orderUnitKey.getOrderUnitKey())
              .orElseThrow(() -> new PaybackException(DOMAIN_ENTITY_002, "orderUnit"));
          final var bundleDiscountPrice = order.getBundleDiscountPrice(orderUnit.getOrderUnitKey());
          final var extraDiscountPrice = order.getExtraDiscountPrice(orderUnit.getOrderUnitKey());

          return SsgPointUnit.readyUnit(
              orderUnitKey.getContrNo(),
              orderUnit.orderUnitPrice(bundleDiscountPrice, extraDiscountPrice),
              policy.getPointExpectSaveAmount(),
              getScheduleDate(order.getOrderDate(), policy),
              policy.getIsSsgPoint(),
              ssgPointState,
              null,
              null);
        })
        .collect(Collectors.toUnmodifiableList());
  }

  private boolean canSave(final boolean isSsgPointPolicy, final boolean isSmilePay) {
    return isSsgPointPolicy && isSmilePay;
  }

  private Instant getScheduleDate(final Instant orderDate, final RewardSsgPointPolicy ssgPointPolicy) {
    return orderDate.plus(ssgPointPolicy.findPolicyDay().orElse(DEFAULT_POLICY_DAY), ChronoUnit.DAYS);
  }

  public SsgPointUnit cancelUnit(final SsgPointTarget ssgPointTarget,
      final SsgPointState ssgPointState,
      final String adminId) {

    return SsgPointUnit.cancelUnit(
        ssgPointTarget.getOrderNo(),
        ssgPointTarget.getPayAmount(),
        ssgPointTarget.getSaveAmount(),
        Instant.parse(ssgPointTarget.getScheduleDate()).plus(1,ChronoUnit.SECONDS),
        ssgPointTarget.getAccountDate(),
        ssgPointTarget.getPointToken(),
        true,
        ssgPointState,
        SsgPointOrigin.builder()
            .orgApproveId(ssgPointTarget.getPntApprId())
            .orgReceiptNo(ssgPointTarget.getReceiptNo())
            .build(),
        adminId
    );
  }

  public SsgPointUnit withholdUnit(final SsgPointTarget ssgPointTarget,
      final SsgPointState ssgPointState,
      final String adminId) {

    return SsgPointUnit.withholdUnit(
        ssgPointTarget.getOrderNo(),
        ssgPointTarget.getPayAmount(),
        ssgPointTarget.getSaveAmount(),
        now(), //보류는 현재날짜 (yyyy-mm-dd)
        true,
        ssgPointState,
        null,
        adminId
    );
  }
}
