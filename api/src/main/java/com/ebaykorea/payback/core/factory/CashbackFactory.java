package com.ebaykorea.payback.core.factory;

import com.ebaykorea.payback.core.domain.entity.cashback.Cashback;
import com.ebaykorea.payback.core.domain.entity.cashback.Cashbacks;
import com.ebaykorea.payback.core.domain.entity.cashback.member.Member;
import com.ebaykorea.payback.core.domain.entity.cashback.policy.Policy;
import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshots;
import com.ebaykorea.payback.core.domain.entity.order.KeyMap;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.payment.Payment;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashbackFactory {

  public Cashbacks createCashback(
      final KeyMap keyMap,
      final Order order,
      final Member member,
      final ItemSnapshots itemSnapshots,
      final Payment payment
      ) {
    return Cashbacks.of(
        order.getOrderKey(),
        keyMap.getPackNo(),
        null, //TODO
        order.getOrderDate(),
        member,
        createCashbacks(),
        createPolicies()
    );
  }

  private List<Cashback> createCashbacks() {
    //TODO
    return Collections.emptyList();
  }

  private List<Policy> createPolicies() {
    //TODO
    return Collections.emptyList();
  }
}
