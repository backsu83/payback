package com.ebaykorea.payback.infrastructure.gateway.client.club;

import com.ebaykorea.payback.infrastructure.gateway.client.club.dto.ClubBaseResponseDto;
import com.ebaykorea.payback.infrastructure.gateway.client.club.dto.ClubDataDto;
import com.ebaykorea.payback.infrastructure.gateway.client.smileclub.SmileClubApiClient;
import com.ebaykorea.payback.util.support.GsonUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
class ClubApiClientTest_ {

    @Autowired
    ClubApiClient clubApiClient;

    @Autowired
    SmileClubApiClient smileClubApiClient;

    @Test
    void getMemberSynopsis() {
        ClubBaseResponseDto<ClubDataDto> memberSynopsis = clubApiClient.getMemberSynopsis("132871942");
        System.out.println(GsonUtils.toJsonPretty(memberSynopsis));
    }

    @Test
    void getMembers() {
        var clubMemberResponseDto = smileClubApiClient.getMembers("S001","132871942");
        System.out.println(GsonUtils.toJsonPretty(clubMemberResponseDto));
    }
}