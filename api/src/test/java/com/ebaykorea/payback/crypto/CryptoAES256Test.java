package com.ebaykorea.payback.crypto;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.junit.jupiter.api.Test;

public class CryptoAES256Test {

  public static final String alg = "AES/CBC/PKCS5Padding";
  private static final String key = "831A667A8A3015F85FF5824DCDFD4C58"; // 32byte
  private static final String iv = "7552B56514CCA47A"; // 16byte

  void cryptText() throws Exception {
    encryptAES256("가나다라나다");
    decryptAES256("bXVpsKaGZ0m3EKQ0sjP/I+YFdpNk4dcOsU5Jhfx1gLk=");
  }

  // 암호화
  public static String encryptAES256(final String text) throws Exception {
    Cipher cipher = Cipher.getInstance(alg);
    SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
    IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
    byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
    return Base64.getEncoder().encodeToString(encrypted);
  }

  // 복호화
  void decryptAES256(String cipherText) throws Exception {
    Cipher cipher = Cipher.getInstance(alg);
    SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
    IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
    cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

    byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
    byte[] decrypted = cipher.doFinal(decodedBytes);
    System.out.println(new String(decrypted, "UTF-8"));
  }
}
