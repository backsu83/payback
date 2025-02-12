package com.ebaykorea.payback.core.dto.ssgpoint;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CancelSsgPointRequestDto {

  @Schema(description = "사이트타입" , defaultValue = "G")
  @NotNull
  private OrderSiteType siteType;

  @Schema(description = "결제번호")
  @NotNull
  private Long packNo;

  @Schema(description = "구매자 아이디")
  private String buyerId;

  @Schema(description = "수동 처리 관리지 아이디")
  private String adminId;

  public SsgPointRequestKey key(long orderNo, String buyerId) {
    return new SsgPointRequestKey(orderNo, buyerId, siteType, PointTradeType.Save);
  }

  public String getAdminId() {
    if(adminId == null) {
      return "payback";
    }
    return adminId;
  }
}
