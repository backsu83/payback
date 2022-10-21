package com.ebaykorea.payback.core.gateway;

import com.ebaykorea.payback.core.domain.entity.cashback.buyer.Club;

import java.util.Optional;

public interface ClubGateway {
    Optional<Club> getMemberSynopsis(String custNo);
}
