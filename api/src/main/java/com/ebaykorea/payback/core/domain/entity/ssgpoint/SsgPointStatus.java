package com.ebaykorea.payback.core.domain.entity.ssgpoint;

import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SsgPointStatus {

  private PointStatusType pointStatusType;
  private PointTradeType pointTradeType;
  private String smileClubCardType;

}
