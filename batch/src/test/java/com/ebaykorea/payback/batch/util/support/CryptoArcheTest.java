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
    String message = "~Yc4z3M9GIq6Pv6veFXkmIA==";
    var result = CryptoArche.decrypt(message, "gmarket");
    System.out.println(result);
    assertEquals(result, "9350163600012428");
  }

  @Test
  void decryptAuction() throws Exception {
    String message = "TOrNDOb49cd7jAaLTZVMMkGFAb4kUp4+";
    var result = CryptoArche.decrypt(message, "auction");
    assertEquals(result, "9350-1302-6783-0229");
  }

  @Test
  void encryptAES256() throws Exception {
    var resultAES256 = CryptoAES256.encrypt("9350163600012428" ,
        "831A667A8A3015F85FF5824DCDFD4C58" ,
        "7552B56514CCA47A");
    assertEquals(resultAES256, "aMjegBR8WyAZRGZzix7Q5AF3Y36yXF+h1YV+XKIMLwE=");

  }
}