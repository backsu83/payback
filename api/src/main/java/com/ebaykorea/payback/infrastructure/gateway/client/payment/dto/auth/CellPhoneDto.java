package com.ebaykorea.payback.infrastructure.gateway.client.payment.dto.auth;

import com.ebaykorea.payback.core.domain.constant.CellPhoneAuthType;
import lombok.Data;

@Data
public class CellPhoneDto {
    String userKey;
    String no;
    String kind;
    CellPhoneAuthType cellPhoneAuthorizationType;
    String ekpgTransactionId;
    String itemName;
    String payerEmail;
    String pgCompanyCode;
    String sellerName;
    String sellerPhone;
    String shopTransactionId;
}
