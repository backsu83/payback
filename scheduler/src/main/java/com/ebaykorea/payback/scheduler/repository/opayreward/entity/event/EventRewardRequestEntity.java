package com.ebaykorea.payback.scheduler.repository.opayreward.entity.event;

import com.ebaykorea.payback.scheduler.model.constant.EventType;
import com.ebaykorea.payback.scheduler.repository.opayreward.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(schema = "O_PAYREWARD", name = "EVENT_REWARD_REQUEST")
public class EventRewardRequestEntity extends BaseEntity {
  @Id
  @Column(name = "EV_RWRD_SEQ")
  private Long requestNo;

  @Column(name = "REQUEST_ID")
  private String requestId;

  @Column(name = "EVENT_TYPE")
  @Enumerated(EnumType.STRING)
  private EventType eventType;

  @Column(name = "USER_TOKEN")
  private String userToken;

  @Column(name = "SAVE_AMOUNT")
  private BigDecimal saveAmount;

  @Column(name = "MESSAGE")
  private String message;

  @Column(name = "TENANT_ID")
  private String tenantId;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "EV_RWRD_SEQ", updatable = false)
  private List<EventRewardRequestStatusEntity> statuses;
}
