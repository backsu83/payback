package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.ssgpoint.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SsgPointTargetEntityId implements Serializable {

  private long orderNo;
  private String buyerId;
  private String siteType;
  private String tradeType;

}


