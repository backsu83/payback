package com.ebaykorea.payback.infrastructure.query;

import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.SsgPointTargetRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgPointTargetEntity;
import com.ebaykorea.payback.infrastructure.query.data.SsgPointTargetQueryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@RequiredArgsConstructor
public class SsgPointQuery {
    private final SsgPointTargetRepository ssgPointTargetRepository;

    private static final String PATTERN_FORMAT = "yyyy-MM-dd";

    public SsgPointTargetQueryResult getSsgPointQueryResult(final Long packNo, String siteType, String tradeType) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT).withZone(ZoneId.of("Asia/Seoul"));
        SsgPointTargetQueryResult result = new SsgPointTargetQueryResult();
        SsgPointTargetEntity target = ssgPointTargetRepository.findByPackNoAndSiteTypeAndTradeType(packNo, siteType, tradeType);
        result.setSsgPointSaveAmount(target.getSaveAmount());
        result.setSsgPointSaveExpectDate(formatter.format(target.getScheduleDate()));
        return result;
    }
}
