package com.ebaykorea.payback.batch.util.support;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
class CryptoArcheTest {

  @Test
  void decryptGmarket() throws Exception {
    String message = "~ok9buyDmfFjZpCUMu7NAfSjwqQo59okydd22eEO96es=";
    var result = CryptoArche.decrypt(message, "gmarket");
    assertEquals(result, "9350-1302-6783-0229");
  }

  @Test
  void decryptAuction() throws Exception {
    String message = "TOrNDOb49cd7jAaLTZVMMkGFAb4kUp4+";
    var result = CryptoArche.decrypt(message, "auction");
    assertEquals(result, "9350-1302-6783-0229");
  }

  @Test
  void encryptAES256() throws Exception {
    var resultAES256 = CryptoAES256.encrypt("9350130267830229" ,
        "831A667A8A3015F85FF5824DCDFD4C58" ,
        "7552B56514CCA47A");
    assertEquals(resultAES256, "Tkwmnpj2FqYDn4FN82i8thYJUs5Eu1xhFaUAgRYakC4=");

  }
}