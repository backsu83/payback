package com.ebaykorea.payback.schedulercluster.repository;


import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface MassSaveRepository<T> {
  List<T> findTargets(final int maxRows, final int maxRetryCount);
  CompletableFuture<Void>  update(final T t);
}
