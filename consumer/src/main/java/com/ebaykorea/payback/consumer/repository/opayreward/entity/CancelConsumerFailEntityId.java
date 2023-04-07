package com.ebaykorea.payback.consumer.repository.opayreward.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelConsumerFailEntityId implements Serializable {

  private Long orderNo;
  private String siteType;
}
