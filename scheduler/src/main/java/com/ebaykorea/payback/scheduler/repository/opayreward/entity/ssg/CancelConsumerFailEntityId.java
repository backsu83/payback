package com.ebaykorea.payback.scheduler.repository.opayreward.entity.ssg;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelConsumerFailEntityId implements Serializable {
    private String siteCode;
    private long orderNo;
}
