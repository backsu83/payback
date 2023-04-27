package com.ebaykorea.payback.infrastructure.gateway

import com.ebaykorea.payback.infrastructure.gateway.client.club.ClubApiClient
import com.ebaykorea.payback.infrastructure.gateway.client.smileclub.SmileClubApiClient
import com.ebaykorea.payback.infrastructure.gateway.mapper.ClubGatewayMapper
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.ClubApiGrocery.SmileClubMemberResponseDto_생성
import static com.ebaykorea.payback.grocery.ClubGrocery.Club_생성

class ClubGatewaySpec extends Specification {
    def clubApiClient = Stub(ClubApiClient)
    def smileClubApiClient = Stub(SmileClubApiClient)
    def clubGatewayMapper = Mappers.getMapper(ClubGatewayMapper)
    def clubGatewayImpl = new ClubGatewayImpl(clubApiClient, smileClubApiClient, clubGatewayMapper)

    def "Club 변환 확인"() {
        setup:
        smileClubApiClient.getMembers(_ as String , _ as String) >> response

        expect:
        def result = clubGatewayImpl.findMembers("custNo")
        result == expectResult

        where:
        desc | response | expectResult
        "Dto 변환"  | SmileClubMemberResponseDto_생성() | Optional.of(Club_생성())
    }
}
