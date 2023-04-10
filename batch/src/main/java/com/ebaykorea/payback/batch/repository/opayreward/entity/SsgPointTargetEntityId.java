package com.ebaykorea.payback.batch.repository.opayreward.entity;


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


