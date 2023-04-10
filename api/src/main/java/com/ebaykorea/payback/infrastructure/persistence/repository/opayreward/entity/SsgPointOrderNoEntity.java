package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(SsgPointOrderNoEntityId.class)
@Table(schema = "O_PAYREWARD", name = "SSG_POINT_ORDER_NO")
public class SsgPointOrderNoEntity extends BaseEntity {

  @Id
  @Column(name = "CANCELED_ORDER_NO")
  private Long orderNo;

  @Id
  @Column(name = "SITE_TYPE")
  private String siteType;
}
