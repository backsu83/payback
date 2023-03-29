package com.ebaykorea.payback.batch.util.support;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CryptoArcheTest {

  @Test
  void decryptGmarket() throws Exception {
    String message = "~ok9buyDmfFjZpCUMu7NAfSjwqQo59okydd22eEO96es=";
    var result = CryptoArche.decryptGmarket(message);
    assertEquals(result, "9350130267830229");
  }

  @Test
  void decryptAuction() throws Exception {
    String message = "TOrNDOb49cd7jAaLTZVMMkGFAb4kUp4+";
    var result = CryptoArche.decryptAuction(message);
    assertEquals(result, "9350130267830229");
  }

  @Test
  void encryptAES256() throws Exception {
//    String message = "TOrNDOb49cd7jAaLTZVMMkGFAb4kUp4+";
//    var resultArche = CryptoArche.decryptAuction(message);
//    assertEquals(resultArche, "9350130267830229");

    var resultAES256 = CryptoAES256.encrypt("9350130267830229" ,
        "831A667A8A3015F85FF5824DCDFD4C58" ,
        "7552B56514CCA47A");
    System.out.println(resultAES256);

  }
}