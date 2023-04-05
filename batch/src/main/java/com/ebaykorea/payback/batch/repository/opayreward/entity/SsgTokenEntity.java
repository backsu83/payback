package com.ebaykorea.payback.batch.repository.opayreward.entity;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "O_PAYREWARD", name = "SSG_TOKEN")
public class SsgTokenEntity extends BaseEntity {

  @Id
  @Column(name = "TOKEN_KEY")
  private String tokenKey;

  @Column(name = "EXPIRE_DATE")
  private Instant expireDate;


}
