package com.ebaykorea.payback.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

//TODO: 임시
public class PaybackOperators {

  private static final int MAX_LENGTH = 50;

  public static String operator(final String defaultValue) {
    try {
      final var hostName = InetAddress.getLocalHost().getHostName();
      return hostName.length() > MAX_LENGTH ? hostName.substring(0, MAX_LENGTH - 1) : hostName;
    } catch (UnknownHostException e) {
      return defaultValue;
    }
  }
}
