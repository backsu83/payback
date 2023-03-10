package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.infrastructure.persistence.mapper.dto.SsgPointTargetResponseDto;
import java.util.List;

public interface SsgPointRepository {

  List<SsgPointTargetResponseDto> save(SsgPoint ssgPoint);
}
