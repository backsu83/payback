package com.ebaykorea.payback.batch.client.ssgpoint.dto;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SsgPointCommonResponse {
  private String clientId;
  private String apiKey;
  private String responseCd;
  private String responseMsg;
  private String tpoint;
  private String ubpoint;
  private String gpoint;
  private String pntApprId;
  private String dupApprid;
  private String dupApoint;

  public String getGpoint() {
    if(StringUtils.isEmpty(gpoint)) {
      return "0";
    }
    return gpoint;
  }

  public String getDupApoint() {
    if(StringUtils.isEmpty(dupApoint)) {
      return "0";
    }
    return dupApoint;
  }
}
