package com.ebaykorea.payback.core.domain.entity.ssgpoint.state;

import static com.ebaykorea.payback.core.domain.constant.PointStatusType.CacnelReady;
import static com.ebaykorea.payback.core.domain.constant.PointStatusType.Cancel;
import static com.ebaykorea.payback.core.domain.constant.PointStatusType.Unknown;
import static com.ebaykorea.payback.core.domain.constant.PointStatusType.WithHold;
import static com.ebaykorea.payback.util.PaybackInstants.now;

import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import java.time.Instant;
import org.springframework.stereotype.Component;

@Component
public class SsgPointStateImpl implements SsgPointState {

  @Override
  public PointStatusType cancelStatus(String pointStatusType, String scheduleDate) {
    switch (PointStatusType.from(pointStatusType)) {
      case Success:
        return Cancel;
      case Ready:
        if (Instant.parse(scheduleDate).isAfter(now())) {
          return CacnelReady;
        } else {
          return WithHold;
        }
    }
    return Unknown;
  }
}
