package com.ebaykorea.payback.infrastructure.gateway.mapper;

import com.ebaykorea.payback.core.domain.entity.cashback.member.Club;
import com.ebaykorea.payback.infrastructure.gateway.client.club.dto.ClubDataDto;
import com.ebaykorea.payback.infrastructure.gateway.client.smileclub.dto.SmileClubMemberResponseDto;
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
    Club map(ClubDataDto clubDataDto);

    @Mapping(source = "smileClubMemberInformation.membershipGrade", target = "membershipGrade")
    @Mapping(source = "smileClubMemberInformation.payCycleType", target = "payCycleType")
    @Mapping(source = "smileClubMemberInformation.joinPartnerId", target = "partnerId")
    @Mapping(source = "smileClubMemberInformation.isSSGMembership", target = "isSSGMembership")
    @Mapping(source = "smileClubMemberInformation.statusCode", target = "statusCode")
    Club map(SmileClubMemberResponseDto smileClubMemberResponseDto);
}
