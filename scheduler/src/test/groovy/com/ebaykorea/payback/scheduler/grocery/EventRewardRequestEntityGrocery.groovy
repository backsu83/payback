package com.ebaykorea.payback.scheduler.grocery

import com.ebaykorea.payback.scheduler.model.constant.EventRequestStatusType
import com.ebaykorea.payback.scheduler.model.constant.EventType
import com.ebaykorea.payback.scheduler.repository.opayreward.entity.event.EventRewardRequestEntity
import com.ebaykorea.payback.scheduler.repository.opayreward.entity.event.EventRewardRequestStatusEntity

class EventRewardRequestEntityGrocery {
  static def EventRewardRequestEntity_생성(Map map = [:]) {
    new EventRewardRequestEntity().tap {
      requestNo = (map.requestNo ?: 1L) as Long
      requestId = (map.requestId ?: "requestId") as String
      eventType = (map.eventType ?: EventType.Toss) as EventType
      userToken = (map.userToken ?: "userToken") as String
      saveAmount = (map.saveAmount ?: 0) as BigDecimal
      message = (map.message ?: "message") as String
      tenantId = (map.tenantId ?: "gmarket") as String
      statuses = (map.statuses ?: [EventRewardRequestStatusEntity_생성(map)]) as List<EventRewardRequestStatusEntity>
    }
  }

  static def EventRewardRequestStatusEntity_생성(Map map = [:]) {
    new EventRewardRequestStatusEntity().tap{
      requestNo = (map.requestNo ?: 1L) as Long
      eventRequestStatus = (map.eventRequestStatus ?: EventRequestStatusType.Created) as EventRequestStatusType
    }
  }
}
