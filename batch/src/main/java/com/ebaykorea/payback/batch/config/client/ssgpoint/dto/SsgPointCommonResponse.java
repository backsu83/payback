package com.ebaykorea.payback.batch.config.client.ssgpoint.dto;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.Data;

@Data
public class SsgPointCommonResponse {
  private String clientId;
  private String apiKey;
  private String responseCd;
  private String responseMsg;
  private String tpoint;
  private String ubpoint;
  private String gpoint;
  private String pntApprId;

  public String getGpoint() {
    if(StringUtils.isEmpty(gpoint)) {
      return "0";
    }
    return gpoint;
  }
}
