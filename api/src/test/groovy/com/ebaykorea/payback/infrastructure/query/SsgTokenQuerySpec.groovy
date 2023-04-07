//package com.ebaykorea.payback.infrastructure.query
//
//import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.SsgTokenRepository
//import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgTokenEntity
//
//import java.time.Duration
//import java.time.Instant
//
//import spock.lang.Specification
//
//class SsgTokenQuerySpec extends Specification {
//
//  def ssgTokenRepository = Mock(SsgTokenRepository)
//  def ssgTokenQuery = new SsgTokenQuery(ssgTokenRepository);
//
//  def "GetSSGAuthToken"() {
//
//
//    when:
//    ssgTokenQuery.getSSGAuthToken()
//
//    then:
//    result == ssgTokenRepository.findTopByExpireDateAfterOrderByExpireDateDesc(param)
//    saveCount * ssgTokenRepository.save(_) >> {}
//
//    where:
//    desc | param | saveCount | result
//    "" | Instant.now() | 1 | null
//    "" | Instant.now().plus(Duration.ofDays(10)) | 0 | SsgTokenEntity.builder().tokenKey("token").build();
//
//  }
//
//}
