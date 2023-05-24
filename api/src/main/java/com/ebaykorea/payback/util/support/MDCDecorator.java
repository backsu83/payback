package com.ebaykorea.payback.util.support;

import java.util.Map;
import java.util.function.Supplier;
import org.slf4j.MDC;

public final class MDCDecorator {

  public static Runnable withMdc(Runnable runnable) {
    Map<String, String> mdc = MDC.getCopyOfContextMap();
    return () -> {
      if (mdc != null) {
        MDC.setContextMap(mdc);
      }
      runnable.run();
    };
  }

  public static <U> Supplier<U> withMdc(Supplier<U> supplier) {
    Map<String, String> mdc = MDC.getCopyOfContextMap();
    return (Supplier) () -> {
      if (mdc != null) {
        MDC.setContextMap(mdc);
      }
      return supplier.get();
    };
  }
}
