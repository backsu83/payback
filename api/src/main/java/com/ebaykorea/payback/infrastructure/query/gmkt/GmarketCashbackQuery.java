package com.ebaykorea.payback.infrastructure.query.gmkt;

import com.ebaykorea.payback.core.query.CashbackQuery;
import com.ebaykorea.payback.infrastructure.gateway.TransactionGatewayImpl;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.CashbackOrderRepository;
import com.ebaykorea.payback.infrastructure.query.data.SavedCashbackQueryResult;
import com.ebaykorea.payback.infrastructure.query.mapper.CashbackOrderQueryDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.GMARKET_TENANT;
import static com.ebaykorea.payback.util.PaybackObjects.isNull;
import static com.ebaykorea.payback.util.PaybackStrings.isBlank;
import static java.util.stream.Collectors.toUnmodifiableList;

@Profile(GMARKET_TENANT)
@Service
@RequiredArgsConstructor
public class GmarketCashbackQuery implements CashbackQuery {

  private final TransactionGatewayImpl transactionGateway;
  private final CashbackOrderRepository cashbackOrderRepository;
  private final CashbackOrderQueryDataMapper cashbackOrderQueryDataMapper;

  @Override
  public SavedCashbackQueryResult getSavedCashback(final Long packNo, final String txKey, final String orderKey) {
    if (!isNull(packNo)) {
      return getSavedCashbackQueryResult(packNo);
    } else if (!isBlank(txKey) && !isBlank(orderKey)) {
      final var keyMap = transactionGateway.getKeyMap(txKey, orderKey);
      return getSavedCashbackQueryResult(keyMap.getPackNo());
    }
    return SavedCashbackQueryResult.EMPTY;
  }

  private SavedCashbackQueryResult getSavedCashbackQueryResult(final Long packNo) {
    final var cashbackOrders = cashbackOrderRepository.findByPackNo(packNo).stream()
        .map(cashbackOrderQueryDataMapper::map)
        .collect(toUnmodifiableList());
    return SavedCashbackQueryResult.of(cashbackOrders);
  }
}
