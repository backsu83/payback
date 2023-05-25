package com.ebaykorea.payback.infrastructure.gateway.mapper;

import com.ebaykorea.payback.core.domain.entity.cashback.member.Club;
import com.ebaykorea.payback.infrastructure.gateway.client.club.dto.ClubDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ClubGatewayMapper {

    @Mapping(source = "member.membershipGrade", target = "membershipGrade")
    @Mapping(source = "member.payCycleType", target = "payCycleType")
    @Mapping(source = "member.partnerId", target = "partnerId")
    @Mapping(source = "member.isSmileClubMember", target = "isSmileClubMember")
    @Mapping(source = "member.isSSGMembership", target = "isSSGMembership")
    @Mapping(source = "member.isSSGPoint", target = "isSSGPoint")
    Club map(ClubDataDto clubDataDto);
}
