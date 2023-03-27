package com.ebaykorea.payback.batch.util.support;

import com.ebay.korea.security.crypto.Cipher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CryptoArche {

  public static String decryptGmarket(final String message) throws Exception {
    Cipher cipher = Cipher.getInstance("gmarket"); // gmarket | auction | auction-aes
    cipher.init(Cipher.DECRYPT_MODE, "gmarket", "credit-card-key");
    String result = cipher.doFinal(message);
    log.info(String.format("crypto-arche-gmarket: {}", result));
    return result;
  }

  public static String decryptAuction(final String message) throws Exception {
    Cipher cipher = Cipher.getInstance("auction"); // gmarket | auction | auction-aes
    cipher.init(Cipher.DECRYPT_MODE, "auction", "credit-card-key");
    String result = cipher.doFinal(message);
    log.info(String.format("crypto-arche-auction: {}", result));
    return result;
  }
}
