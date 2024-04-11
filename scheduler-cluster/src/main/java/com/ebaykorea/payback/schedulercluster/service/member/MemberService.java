package com.ebaykorea.payback.schedulercluster.service.member;

import java.util.concurrent.CompletableFuture;

public interface MemberService {

  CompletableFuture<String> findSmileUserKeyAsync(final String memberKey);

}
