package com.ebaykorea.payback.core.domain.entity.ssgpoint;

import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class SsgPointAuth {
    String clientId;
    String apiKey;
}
