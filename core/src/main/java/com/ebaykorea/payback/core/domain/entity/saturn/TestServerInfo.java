package com.ebaykorea.payback.core.domain.entity.saturn;

import lombok.Data;

import javax.persistence.Column;

@Data
public class TestServerInfo {

  @Column(name = "attribute_id")
  private int attributeId;

  @Column(name = "attribute_name")
  private String attributeName;

  @Column(name = "attribute_value")
  private String attributeValue;

}

