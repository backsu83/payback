package com.ebaykorea.payback.infrastructure.query;

import com.ebaykorea.payback.infrastructure.query.mapper.SsgPointTargetQueryResultMapper;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.SsgPointTargetRepository;
import com.ebaykorea.payback.infrastructure.query.data.SsgPointTargetQueryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SsgPointQuery {
    private final SsgPointTargetRepository ssgPointTargetRepository;
    private final SsgPointTargetQueryResultMapper pointMapper;

    public List<SsgPointTargetQueryResult> getSsgPointQueryResult(final Long packNo, String siteType, String tradeType) {
        return ssgPointTargetRepository.findByPackNo(packNo)
                .stream().filter(s -> siteType.equals(s.getSiteType()) && tradeType.equals(s.getTradeType()))
                .map(pointMapper::map)
                .collect(Collectors.toUnmodifiableList());
    }
}
