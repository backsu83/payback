package com.ebaykorea.payback.batch.util.support;

import com.ebay.korea.security.crypto.Cipher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CryptoArche {

  public static String decrypt(final String message , final String instance) throws Exception {
    Cipher cipher = Cipher.getInstance(instance); // gmarket | auction | auction-aes
    cipher.init(Cipher.DECRYPT_MODE, instance, "credit-card-key");
    String result = cipher.doFinal(message);
    return result;
  }
}
