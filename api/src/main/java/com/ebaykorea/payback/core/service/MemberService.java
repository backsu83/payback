package com.ebaykorea.payback.core.service;

import com.ebaykorea.payback.core.domain.entity.cashback.member.Member;
import com.ebaykorea.payback.core.domain.entity.cashback.member.Club;
import com.ebaykorea.payback.core.domain.entity.order.OrderBuyer;
import com.ebaykorea.payback.core.gateway.ClubGateway;
import com.ebaykorea.payback.core.gateway.UserGateway;
import com.ebaykorea.payback.util.PaybackStrings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final ClubGateway clubGateway;
  private final UserGateway userGateway;

  public Member getMember(final OrderBuyer orderBuyer) {
    final var clubAsync = getClubAsync(orderBuyer.getBuyerNo());
    final var userKeyAsync = getUserKeyAsync(orderBuyer.getBuyerNo());

    final var club = clubAsync.join();
    final var userKey = userKeyAsync.join();

    return Member.of(
        orderBuyer.getBuyerNo(),
        orderBuyer.getBuyerId(),
        userKey,
        orderBuyer.isMember(),
        orderBuyer.isSmileClubMember(),
        club);
  }

  private CompletableFuture<Club> getClubAsync(final String buyerNo) {
    return CompletableFuture.supplyAsync(() -> clubGateway.findMemberSynopsis(buyerNo))
        .thenApply(club -> club.orElse(null));
  }

  private CompletableFuture<String> getUserKeyAsync(final String buyerNo) {
    return CompletableFuture.supplyAsync(() -> userGateway.findUserKey(buyerNo))
        .thenApply(userKey -> userKey.orElse(PaybackStrings.EMPTY));
  }
}
