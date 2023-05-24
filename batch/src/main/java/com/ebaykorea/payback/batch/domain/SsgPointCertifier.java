package com.ebaykorea.payback.batch.domain;

import com.ebaykorea.payback.batch.config.properties.SsgPointAuthProperties;
import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import lombok.Value;

@Value
public class SsgPointCertifier {

  String memberKey;
  String clientId;
  String apiKey;
  String encryptKey;
  String encryptIv;
  String decryptInstance;
  String branchId;

  private SsgPointCertifier(final String memberKey,
      final String clientId,
      final String apiKey,
      final String encryptKey,
      final String encryptIv,
      final String decryptInstance,
      final String branchId) {
    this.memberKey = memberKey;
    this.clientId = clientId;
    this.apiKey = apiKey;
    this.encryptKey = encryptKey;
    this.encryptIv = encryptIv;
    this.decryptInstance = decryptInstance;
    this.branchId = branchId;
  }

  public static SsgPointCertifier of(SsgPointAuthProperties properties, OrderSiteType orderSiteType) {
    if(orderSiteType == OrderSiteType.Gmarket) {
      final var gmarket = properties.getGmarket();
      return new SsgPointCertifier(gmarket.getMemberKey(),
          gmarket.getClientId(),
          gmarket.getApiKey(),
          gmarket.getEncryptKey(),
          gmarket.getEncryptIv(),
          gmarket.getDecryptInstance(),
          gmarket.getBranchId());
    } else {
      final var auction = properties.getAuction();
      return new SsgPointCertifier(auction.getMemberKey(),
          auction.getClientId(),
          auction.getApiKey(),
          auction.getEncryptKey(),
          auction.getEncryptIv(),
          auction.getDecryptInstance(),
          auction.getBranchId());
    }
  }
}
