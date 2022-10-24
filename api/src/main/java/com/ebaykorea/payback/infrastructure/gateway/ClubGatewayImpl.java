package com.ebaykorea.payback.infrastructure.gateway;

import com.ebaykorea.payback.core.domain.entity.cashback.buyer.Club;
import com.ebaykorea.payback.core.exception.PaybackException;
import com.ebaykorea.payback.core.exception.PaybackExceptionCode;
import com.ebaykorea.payback.core.gateway.ClubGateway;
import com.ebaykorea.payback.infrastructure.gateway.client.club.ClubApiClient;
import com.ebaykorea.payback.infrastructure.gateway.client.club.dto.ClubBaseResponseDto;
import com.ebaykorea.payback.infrastructure.gateway.client.club.dto.ClubDataDto;
import com.ebaykorea.payback.infrastructure.mapper.ClubGatewayMapper;
import com.ebaykorea.payback.infrastructure.persistence.redis.support.GsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClubGatewayImpl implements ClubGateway {
    private final ClubApiClient clubApiClient;
    private final ClubGatewayMapper clubGatewayMapper;

    @Override
    public Optional<Club> findMemberSynopsis(String custNo) {
        return Optional.of(clubGatewayMapper
                .map(clubApiClient
                        .getMemberSynopsis(custNo)
                        .getData()));
    }
}
