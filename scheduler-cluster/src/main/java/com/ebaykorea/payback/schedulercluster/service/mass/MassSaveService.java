package com.ebaykorea.payback.schedulercluster.service.mass;


import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface MassSaveService<T> {
  List<T> findTargets(final int maxRows, final int maxRetryCount);
  CompletableFuture<Void>  update(final T t);
}
