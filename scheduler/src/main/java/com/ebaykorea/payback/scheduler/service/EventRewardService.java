package com.ebaykorea.payback.scheduler.service;

import com.ebaykorea.payback.scheduler.repository.opayreward.EventRewardRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventRewardService {
  private final EventRewardRepositoryCustom repositoryCustom;

  public void run() {
    final var notRequestedTargets = repositoryCustom.findNotRequestedRequests();

    //TODO

  }
}
