package com.ebaykorea.payback.batch.client.ssgpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SsgPointVerifyResponse {
    private String clientId;
    private String apiKey;
    private String responseCd;
    private String responseMsg;
}
