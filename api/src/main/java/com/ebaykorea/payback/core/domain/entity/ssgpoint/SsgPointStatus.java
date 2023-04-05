package com.ebaykorea.payback.core.domain.entity.ssgpoint;

import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SsgPointStatus {

  PointStatusType pointStatusType;
  PointTradeType pointTradeType;
  String smileClubCardType;

}
