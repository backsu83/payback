package com.ebaykorea.payback.core.ssgpoint.service;

import com.ebaykorea.payback.api.dto.SaveSsgPointRequest;
import com.ebaykorea.payback.core.domain.entity.reward.RewardSsgPointPolicy;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointCard;
import com.ebaykorea.payback.core.gateway.OrderGateway;
import com.ebaykorea.payback.core.gateway.PaymentGateway;
import com.ebaykorea.payback.core.gateway.RewardGateway;
import com.ebaykorea.payback.core.gateway.SmileClubGateway;
import com.ebaykorea.payback.core.gateway.TransactionGateway;
import com.ebaykorea.payback.core.repository.SsgPointRepository;
import com.ebaykorea.payback.core.ssgpoint.SsgPointCreater;
import com.ebaykorea.payback.infrastructure.persistence.mapper.dto.SsgPointTargetResponseDto;
import com.ebaykorea.payback.util.support.GsonUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SsgPointGmarketService {

  private final OrderGateway orderGateway;
  private final TransactionGateway transactionGateway;
  private final PaymentGateway paymentGateway;
  private final RewardGateway rewardGateway;
  private final SmileClubGateway smileClubGateway;
  private final SsgPointCreater ssgPointCreater;
  private final SsgPointRepository ssgPointRepository;

  public List<SsgPointTargetResponseDto> setSsgPoint(SaveSsgPointRequest request) {

    final var order = orderGateway.getOrder(request.getOrderNo());
    final var orderKeyMap = transactionGateway.getKeyMap(request.getTxKey(), request.getOrderNo());
    final var paymentRecordFuture = paymentGateway.getPaymentRecordAsync(order.getPaySeq());
    final var itemSnapshotsFuture = orderGateway.getItemSnapshotAsync(order.findItemSnapshotKeys());

    final var paymentRecord = paymentRecordFuture.join();
    final var itemSnapshots = itemSnapshotsFuture.join();

    final var ssgPointPolicies = rewardGateway.getSsgPointPolicies(order,
        paymentRecord,
        itemSnapshots.getItemSnapshotMap(),
        orderKeyMap.orderUnitKeyMap());

//    final var ssgPointCard = smileClubGateway.getCardNo("","");
    final var ssgPointCard =  SsgPointCard.builder().cardNo("enDDFdsfhhsdf==").build(); //임시

    final var ssgPoint = ssgPointCreater.create(ssgPointPolicies, order, orderKeyMap, ssgPointCard);
    log.info("create ssgpoint: {}" , GsonUtils.toJsonPretty(ssgPoint));
    return ssgPointRepository.save(ssgPoint);
  }
}
