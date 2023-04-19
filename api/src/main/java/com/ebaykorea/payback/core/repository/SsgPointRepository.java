package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.dto.SsgPointDto;
import com.ebaykorea.payback.core.dto.SsgPointOrderNoDto;
import com.ebaykorea.payback.core.dto.SsgPointTargetResponseDto;
import com.ebaykorea.payback.core.dto.VerifyDailySsgPointDto;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SsgPointRepository {

  List<SsgPointTargetResponseDto> save(SsgPoint ssgPoint);

  SsgPointDto findByPointStatusReady(long orderNo , String buyerId, OrderSiteType siteType);

  int updatePointStatus(String pointStatus, @Nullable String manualOprt, String updateOperator, Instant updateDate, @NonNull Long orderNo, @NonNull String buyerId, @NonNull String siteType, @NonNull String tradeType);

  int retryFailPointStatus(String manualOprt, String updateOperator, Instant updateDate, Long orderNo, String buyerId, String siteType, String tradeType);

  Optional<SsgPointTargetResponseDto> findByKey(Long orderId, String buyerId, String siteType, String tradeType);

  void setCancelOrderNoNoneSave(SsgPointOrderNoDto ssgPointOrderNoDto);

  VerifyDailySsgPointDto verifyDailyPoint(VerifyDailySsgPointDto verifyDailySsgPointDto);
}
