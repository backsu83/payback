package com.ebaykorea.payback.infrastructure.query;

import com.ebaykorea.payback.infrastructure.query.data.SavedCashbackQueryResult;

public interface CashbackQuery {
  SavedCashbackQueryResult getSavedCashback(final Long packNo, final String txKey, final String orderKey);
}
