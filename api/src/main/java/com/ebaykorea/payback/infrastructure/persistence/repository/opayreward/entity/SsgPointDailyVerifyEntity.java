package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "O_PAYREWARD", name = "SSG_POINT_DAILY_VERIFY")
public class SsgPointDailyVerifyEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SSG_POINT_DAILY_VERIFY_generator")
  @SequenceGenerator(name = "SSG_POINT_DAILY_VERIFY_generator",
          sequenceName = "SEQ_SSG_POINT_DAILY_VERIFY", allocationSize = 1)
  @Column(name = "SSG_POINT_DAILY_VERIFY_SEQ")
  private long ssgPointDailyVerifySeq;

  @Column(name = "TRADE_DATE")
  private String tradeDate;

  @Column(name = "SITE_TYPE")
  private String siteType;

  @Column(name = "TRADE_TYPE")
  private String tradeType;

  @Column(name = "COUNT")
  private long count;

  @Column(name = "AMOUNT")
  private BigDecimal amount;

  @Column(name = "RETURN_CODE")
  private String returnCode;

  @Column(name = "RETURN_MSG")
  private String returnMessage;
}
