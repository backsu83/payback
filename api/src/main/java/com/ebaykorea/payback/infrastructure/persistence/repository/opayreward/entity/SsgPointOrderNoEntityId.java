package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SsgPointOrderNoEntityId implements Serializable {
  private Long orderNo;
  private String siteType;
}
