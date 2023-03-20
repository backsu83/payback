package com.ebaykorea.payback.infrastructure.persistence.repository;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit;
import com.ebaykorea.payback.core.dto.SsgPointDto;
import com.ebaykorea.payback.core.repository.SsgPointRepository;
import com.ebaykorea.payback.infrastructure.persistence.mapper.SsgPointTargetEntityMapper;
import com.ebaykorea.payback.core.dto.SsgPointTargetResponseDto;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.SsgPointTargetRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.SsgPointTargetRepositorySupport;
import com.google.common.collect.Lists;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SsgPointRepositoryImpl implements SsgPointRepository {

  private final SsgPointTargetRepository ssgPointTargetRepository;
  private final SsgPointTargetEntityMapper ssgPointTargetEntityMapper;
  private final SsgPointTargetRepositorySupport ssgPointTargetRepositorySupport;

  @Transactional
  @Override
  public List<SsgPointTargetResponseDto> save(final SsgPoint ssgPoint) {
    List<SsgPointTargetResponseDto> sspointTargetList = Lists.newArrayList();
    ssgPoint.getSsgPointUnits().stream()
        .filter(SsgPointUnit::getIsPolicy)
        .forEach(unit->{
          sspointTargetList.add(saveSsgTarget(ssgPoint , unit));
    });
    return sspointTargetList;
  }

  private SsgPointTargetResponseDto saveSsgTarget(final SsgPoint ssgPoint ,final SsgPointUnit ssgPointUnit) {
    final var ssgPointTargetEntity = ssgPointTargetEntityMapper.map(ssgPoint, ssgPointUnit);
    return ssgPointTargetEntityMapper.mapToSsgTarget(ssgPointTargetRepository.save(ssgPointTargetEntity));
  }

  @Transactional(readOnly = true)
  @Override
  public SsgPointDto findByPointStatus(final long orderNo, final OrderSiteType siteType) {
    final var ssgPointTargetEntity = ssgPointTargetRepositorySupport.findByPointStatus(orderNo, siteType);
    return ssgPointTargetEntityMapper.mapToPointCancel(ssgPointTargetEntity);
  }
}
