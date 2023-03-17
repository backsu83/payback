package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.dto.SsgPointCancedDto;
import com.ebaykorea.payback.core.dto.SsgPointTargetResponseDto;
import java.util.List;

public interface SsgPointRepository {

  List<SsgPointTargetResponseDto> save(SsgPoint ssgPoint);

  SsgPointCancedDto findByPointStatus(long orderNo , OrderSiteType siteType);
}
