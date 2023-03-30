package com.ebaykorea.payback.batch.util.support;

import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import org.junit.jupiter.api.Test;

public class PaybackInstanceTest {

  @Test
  void instanceTest() {
    var gmarket = OrderSiteType.Gmarket.toString().toLowerCase();
    System.out.println(gmarket);

  }
}
