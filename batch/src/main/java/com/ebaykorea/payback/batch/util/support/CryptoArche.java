package com.ebaykorea.payback.batch.util.support;

import com.ebay.korea.security.crypto.Cipher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CryptoArche {

  public static String decrypt(final String message , final String siteType) throws Exception {
    Cipher cipher = Cipher.getInstance(siteType); // gmarket | auction | auction-aes
    cipher.init(Cipher.DECRYPT_MODE, siteType, "credit-card-key");
    String result = cipher.doFinal(message);
    log.info(String.format("crypto-arche-" + siteType + ": {}", result));
    return result;
  }
}
