package com.ebaykorea.payback.consumer.listener

import com.ebaykorea.payback.consumer.event.OrderCanceledAuctionEvent
import com.ebaykorea.payback.consumer.event.OrderSiteType
import com.ebaykorea.payback.consumer.service.RequestSsgPointService
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.listener.ListenerExecutionFailedException
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.messaging.support.GenericMessage
import spock.lang.Ignore
import spock.lang.Specification


@Ignore
@SpringBootTest
@EmbeddedKafka(
        partitions = 1,
        brokerProperties = ["listeners=PLAINTEXT://localhost:9092", "port=9092"]
)
class OrderCanceledAuctionListenerSpec extends Specification {

  def requestSsgPointService = Mock(RequestSsgPointService)
  def listener = new OrderCanceledAuctionListener(requestSsgPointService)

  def "옥션_카프카_취소이벤트_컨슘_성공"() {
    given:
    def event = new OrderCanceledAuctionEvent(payNo: 123, orderNo: 456)

    when:
    listener.consumeForSsgPoints(event)

    then:
    1 * requestSsgPointService.cancelSsgPointAuction(_ as Long, _ as Long)
    0 * requestSsgPointService.saveError(_ as Long, _ as Long, OrderSiteType.Auction, -2L, _ as String, _ as String)

  }

  def "옥션_카프카_취소이벤트_컨슘_실패"() {
    given:
    def event = new OrderCanceledAuctionEvent(payNo: 123, orderNo: 456)
    def exception = new ListenerExecutionFailedException(_ as String)

    when:
    listener.consumeForSsgPoints(event)
    def result = listener.consumeForSsgPointsErrorHandler().handleError(new GenericMessage<>(event) , exception)

    then:
    1 * requestSsgPointService.cancelSsgPointAuction(_ as Long, _ as Long)
    1 * requestSsgPointService.saveError(_ as Long, _ as Long, OrderSiteType.Auction, -2L, _ as String, _ as String)
    result == null
  }
}
