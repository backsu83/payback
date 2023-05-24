package com.ebaykorea.payback.batch.util.support;


import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoAES256 {

  public static final String alg = "AES/CBC/PKCS5Padding";
  // 암호화
  public static String encrypt(final String text , final String key , final String iv) throws Exception {
    Cipher cipher = Cipher.getInstance(alg);
    SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
    IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
    byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
    return Base64.getEncoder().encodeToString(encrypted);
  }

  // 복호화
  public static String decrypt(final String cipherText ,  final String key , final String iv) throws Exception {
    Cipher cipher = Cipher.getInstance(alg);
    SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
    IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
    cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);
    byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
    byte[] decrypted = cipher.doFinal(decodedBytes);
    return new String(decrypted, "UTF-8");
  }
}