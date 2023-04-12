package com.ebaykorea.payback.scheduler.repository.opayreward.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancelConsumerFailEntityId {
    private String siteCode;
    private long orderNo;
}
