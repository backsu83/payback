package com.ebaykorea.payback.infrastructure.gateway.client.club

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ClubApiClientTest extends Specification {

    @Autowired
    ClubApiClient clubApiClient;

    def "GetMemberSynopsis"() {
        setup:
        def memberid = '103574394';

        expect:
        def result = clubApiClient.getMemberSynopsis(memberid);
        println result;

    }
}
