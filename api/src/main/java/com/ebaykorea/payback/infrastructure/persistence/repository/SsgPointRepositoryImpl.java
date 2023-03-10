package com.ebaykorea.payback.infrastructure.persistence.repository;

import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit;
import com.ebaykorea.payback.core.repository.SsgPointRepository;
import com.ebaykorea.payback.infrastructure.persistence.mapper.SsgPointTargetEntityMapper;
import com.ebaykorea.payback.infrastructure.persistence.mapper.dto.SsgPointTargetResponseDto;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.SsgPointTargetRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.SsgPointTargetRepositorySupport;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgPointTargetEntity;
import com.ebaykorea.payback.util.support.GsonUtils;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SsgPointRepositoryImpl implements SsgPointRepository {

  private final SsgPointTargetRepositorySupport ssgPointTargetRepositorySupport;
  private final SsgPointTargetRepository ssgPointTargetRepository;
  private final SsgPointTargetEntityMapper ssgPointTargetEntityMapper;

  @Transactional
  public List<SsgPointTargetResponseDto> save(SsgPoint ssgPoint) {
    List<SsgPointTargetResponseDto> sspointTargetList = Lists.newArrayList();
    ssgPoint.getSsgPointUnits().stream()
        .forEach(unit->{
          sspointTargetList.add(saveSsgPiontUnit(ssgPoint , unit));
    });
    return sspointTargetList;
  }

  private SsgPointTargetResponseDto saveSsgPiontUnit(final SsgPoint ssgPoint ,final SsgPointUnit ssgPointUnit) {
    final var ssgPointTargetEntity = ssgPointTargetEntityMapper.map(ssgPoint, ssgPointUnit);
    return ssgPointTargetEntityMapper.mapToSsgTarget(ssgPointTargetRepository.save(ssgPointTargetEntity));
  }
}
