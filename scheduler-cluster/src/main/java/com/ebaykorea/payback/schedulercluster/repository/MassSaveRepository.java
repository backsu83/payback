package com.ebaykorea.payback.schedulercluster.repository;


import com.ebaykorea.payback.schedulercluster.model.MassSaveEvent;
import java.util.List;


public interface MassSaveRepository {
  List<MassSaveEvent> findTargets(final int maxRows, final int maxRetryCount);
  void updateSaveStatus(final String userKey, final MassSaveEvent entity);
  void updateRetryCount(final MassSaveEvent entity);

}
