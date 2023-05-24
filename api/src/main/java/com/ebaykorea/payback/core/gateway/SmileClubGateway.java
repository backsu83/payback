package com.ebaykorea.payback.core.gateway;

import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointCard;

public interface SmileClubGateway {

  SsgPointCard getCardNo(String partnerId, String partnerMemberKey);

}
