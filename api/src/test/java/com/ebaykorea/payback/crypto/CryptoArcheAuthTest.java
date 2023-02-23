package com.ebaykorea.payback.crypto;



import com.ebay.korea.security.crypto.Cipher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CryptoArcheAuthTest {

  @Test
  void encryptGmarket() throws Exception {
    String message = "가나다라 ㄱ힣 ABCD 1234 !@#$%^&*()_+";
    Cipher cipher = Cipher.getInstance("gmarket"); // gmarket | auction | auction-aes
    cipher.init(Cipher.ENCRYPT_MODE, "private-info-key"); // "login-key" | "api-key" | "ssn-key" | "credit-card-key" | "private-info-key" | "cookie-key" | "auction-key" | "gmarket-key" | "smilepay-key" | "gcryptocom-key" | "gcrypto-key"
    String result = cipher.doFinal(message);
    System.out.println(String.format("gmarket-result[%s]", result));
  }

  @Test
  void decryptGmarket() throws Exception {
    String message = "~JCUbDLN63zac2KQjIGqikA3RTTMBbkk4A0ISrpmnHAEgNLp8fsrGvmYnnMv84WAs";
    Cipher cipher = Cipher.getInstance("gmarket"); // gmarket | auction | auction-aes
    cipher.init(Cipher.DECRYPT_MODE, "test-keys", "private-info-key"); // "login-key" | "api-key" | "ssn-key" | "credit-card-key" | "private-info-key" | "cookie-key" | "auction-key" | "gmarket-key" | "smilepay-key" | "gcryptocom-key" | "gcrypto-key"
    String result = cipher.doFinal(message);
    System.out.println(String.format("gmarket-result[%s]", result));
  }

  @Test
  void encryptAuction() throws Exception {
    String message = "가나다라 ㄱ힣 ABCD 1234 !@#$%^&*()_+";
    Cipher cipher = Cipher.getInstance("auction"); // gmarket | auction | auction-aes
    cipher.init(Cipher.ENCRYPT_MODE, "test-keys", "private-info-key"); // "login-key" | "api-key" | "ssn-key" | "credit-card-key" | "private-info-key" | "cookie-key" | "auction-key" | "gmarket-key" | "smilepay-key"
    String result = cipher.doFinal(message);
    System.out.println(String.format("auction-result[%s]", result));
  }

  @Test
  void decryptAuction() throws Exception {
    String message = "7UeDe7OqEHuZMY13LDQx4NUiOAfVShT3qXvGtf/1GhnDm786baq3HFsrwzGpnVmV";
    Cipher cipher = Cipher.getInstance("auction"); // gmarket | auction | auction-aes
    cipher.init(Cipher.DECRYPT_MODE, "test-keys", "private-info-key"); // "login-key" | "api-key" | "ssn-key" | "credit-card-key" | "private-info-key" | "cookie-key" | "auction-key" | "gmarket-key" | "smilepay-key"
    String result = cipher.doFinal(message);
    System.out.println(String.format("auction-result[%s]", result));
  }

  @Test
  void encryptAuctionAes() throws Exception {
    String message = "가나다라 ㄱ힣 ABCD 1234 !@#$%^&*()_+";
    Cipher cipher = Cipher.getInstance("auction-aes"); // gmarket | auction | auction-aes
    cipher.init(Cipher.ENCRYPT_MODE, "test-keys", "private-info-key"); // "login-key" | "api-key" | "ssn-key" | "credit-card-key" | "private-info-key" | "cookie-key" | "auction-key" | "gmarket-key" | "smilepay-key"
    String result = cipher.doFinal(message);
    System.out.println(String.format("auction-aes-enc-result[%s]", result));
  }

  @Test
  void decryptAuctionAes() throws Exception {
    String message = "~JCUbDLN63zac2KQjIGqikA3RTTMBbkk4A0ISrpmnHAEgNLp8fsrGvmYnnMv84WAs";
    Cipher cipher = Cipher.getInstance("auction-aes"); // gmarket | auction | auction-aes
    cipher.init(Cipher.DECRYPT_MODE, "test-keys", "private-info-key"); // "login-key" | "api-key" | "ssn-key" | "credit-card-key" | "private-info-key" | "cookie-key" | "auction-key" | "gmarket-key" | "smilepay-key"
    String result = cipher.doFinal(message);
    System.out.println(String.format("auction-aes-dec-result[%s]", result));
  }

  /**
   * $ echo -n 'abcd가나다라1234!@#$' | sha512sum | awk '{print $1}'
   * result: 0ae59bce8fd9da62276aa1d7eef5a8e6e811dce219d1cda757e5b6605d9fce430cc0666f7aaf9b91eccdc2a3c4c04d19d3b22f9c6b1b296f1ee2ba209ddd8a4f
   */
  @Test
  void hash() {
    //String message = "가나다라 ㄱ힣 ABCD 1234 !@#$%^&*()_+";
    //String salt = "1234";
    String message = "abcd가나다라1234!@#$";
    String salt = "";
    Cipher cipher = Cipher.getInstance("gmarket"); // gmarket | auction
    cipher.update(salt);
    String result = cipher.digest(message);
    System.out.println(String.format("hash-result[%s]", result));
  }

}