package com.ebaykorea.payback.consumer.listener


import com.ebaykorea.payback.consumer.event.OrderCanceledAuctionEvent
import com.ebaykorea.payback.consumer.event.OrderSiteType
import com.ebaykorea.payback.consumer.service.CancelSsgPointService
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.context.EmbeddedKafka
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
@SpringBootTest
@EmbeddedKafka(
        partitions = 1,
        brokerProperties = ["listeners=PLAINTEXT://localhost:9092", "port=9092"]
)
class OrderCanceledAuctionListenerSpec extends Specification {

  def requestSsgPointService = Mock(CancelSsgPointService)
  def listener = new OrderCanceledListener(requestSsgPointService)

  def "옥션_카프카_취소이벤트_컨슘_성공"() {
    given:
    def event = new OrderCanceledAuctionEvent(payNo: 123, orderNo: 456)

    when:
    listener.consumeForSsgPoints(event)

    then:
    1 * requestSsgPointService.cancelSsgPoint(_ as Long, _ as Long)
    0 * requestSsgPointService.saveError(_ as Long, _ as Long, OrderSiteType.Auction, -2L, _ as String, _ as String)

  }

//  def "옥션_카프카_취소이벤트_컨슘_실패"() {
//    given:
//    def event = new OrderCanceledAuctionEvent(payNo: 123, orderNo: 456)
//
//    when:
//    listener.consumeForSsgPoints(event)
//
//    then:
//    1 * requestSsgPointService.cancelSsgPointAuction(_ as Long, _ as Long)
//    0 * requestSsgPointService.saveError(_ as Long, _ as Long, OrderSiteType.Auction, -2L, _ as String, _ as String)
//  }
}
