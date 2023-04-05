package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.infrastructure.persistence.repository.customer.entity.SmilePointTradeEntity
import com.ebaykorea.payback.infrastructure.query.data.SmilePointTradeQueryResult

import java.time.Instant

class SmilePointTradeGrocery {

  static def SmilePointTradeQueryResult_생성(Map map = [:]) {
    new SmilePointTradeQueryResult(
        (map.smilePayNo ?: 1L) as long,
        (map.buyerNo ?: "buyerNo") as String,
        (map.point ?: 0) as int,
        (map.contrNo ?: 1L) as long,
        (map.comment ?: "comment") as String,
        (map.reasonCode ?: 0) as int,
        (map.apprStatus ?: 0) as int,
        (map.apprStatusName ?: "apprStatusName") as String,
        (map.targetType ?: 0) as int,
        (map.targetTypeName ?: "targetTypeName") as String,
        (map.saveType ?: "saveType") as String,
        (map.errorMsg ?: "errorMsg") as String,
        (map.certApprId ?: "certApprId") as String,
        (map.userKey ?: "userKey") as String,
        (map.regDate ?: "2023-03-02 13:34:00") as String,
        (map.expireDate ?: "2023-03-02 13:34:00") as String
    )
  }

  static def SmilePointTradeEntity_생성(Map map = [:]) {
    new SmilePointTradeEntity(
        (map.smilePayNo ?: 1L) as long,
        (map.custNo ?: "buyerNo") as String,
        (map.contrNo ?: 1L) as long,
        (map.point ?: 0) as int,
        (map.comment ?: "comment") as String,
        (map.reasonCode ?: 0) as int,
        (map.refundReasonCode ?: 0) as int,
        (map.ersNo ?: 0) as int,
        (map.eId ?: 0) as int,
        (map.winNo ?: 0L) as long,

        (map.issuer ?: 0) as int,
        (map.issuer_id ?: "issuer_id") as String,
        (map.regDt ?: "2023-03-02 13:34:00") as String,
        (map.regId ?: "regId") as String,
        (map.chgDt ?: "2023-03-02 13:34:00") as String,
        (map.chgId ?: "chgId") as String,
        (map.certApprId ?: "certApprId") as String,
        (map.smileToken ?: "smileToken") as String,
        (map.serviceType ?: "saveType") as String,
        (map.certCode ?: "certCode") as String,

        (map.certClass ?: "certClass") as String,
        (map.smilePointCode ?: "smilePointCode") as String,
        (map.returnCode ?: "returnCode") as String,
        (map.returnValue ?: "returnValue") as String,
        (map.errorMassage ?: "errorMsg") as String,
        (map.acquireDate ?: null) as Instant,
        (map.orgCertApprId ?: "orgCertApprId") as String,
        (map.orgSmilePayNo ?: 1L) as long,
        (map.cancelCode ?: "cancelCode") as String,
        (map.plusMoney ?: null) as BigDecimal,

        (map.minusMoney ?: null) as BigDecimal,
        (map.cancelDate ?: null) as String,
        (map.userKey ?: "userKey") as String,
        (map.expireDate ?: "2023-03-02 13:34:00") as String,
        (map.returnExpireDate ?: "2023-03-02 13:34:00") as String,
        (map.orgMoney ?: null) as BigDecimal,
        (map.cancelMoney ?: null) as BigDecimal,
        (map.cancelPlusMoney ?: null) as BigDecimal,
        (map.cancelMinusMoney ?: null) as BigDecimal,
        (map.remainMoney ?: null) as BigDecimal,

        (map.remainPlusMoney ?: null) as BigDecimal,
        (map.remainMinusMoney ?: null) as BigDecimal,
        (map.certDate ?: "2023-03-02 13:34:00") as String,
        (map.certId ?: "certId") as String,
        (map.apprDate ?: "2023-03-02 13:34:00") as String,
        (map.apprId ?: "apprId") as String,
        (map.apprStatus ?: 0) as int,
        (map.targetType ?: 0) as int,
        (map.daemonExecDate ?: null) as String,
        (map.daemonExecCount ?: 0) as int,

        (map.etcComment ?: "etcComment") as String,
        (map.expiredMoney ?: null) as BigDecimal,
        (map.subShpoId ?: "subShpoId") as String
    )

  }
}
