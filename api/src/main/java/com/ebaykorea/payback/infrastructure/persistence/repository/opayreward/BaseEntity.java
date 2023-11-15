package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity implements Serializable {

  @Column(name = "INS_DATE", nullable = false, updatable = false)
  @CreatedDate
  private Instant insertDate;

  @Column(name = "INS_OPRT", nullable = false, updatable = false)
  @CreatedBy
  private String insertOperator;

  @Column(name = "UPD_DATE", nullable = false)
  @LastModifiedDate
  private Instant updateDate;

  @Column(name = "UPD_OPRT", nullable = false)
  @LastModifiedBy
  private String updateOperator;
}
