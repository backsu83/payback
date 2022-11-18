package com.ebaykorea.payback.infrastructure.gateway.client.reward.dto;

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.GATEWAY_002;
import static org.junit.jupiter.api.Assertions.*;

import com.ebaykorea.payback.core.exception.PaybackException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

class RewardBaseReturnTest {

//  @Test
//  void name() {
//    Optional<RewardBaseReturn> rewardBaseReturn = Optional.of(RewardBaseReturn.builder()
//        .returnCode("0000")
//        .returnValue("success")
//        .errorMessage("errror")
//        .build());
//
//    rewardBaseReturn = null;
//
//    Optional.ofNullable(rewardBaseReturn)
//        .filter(Optional::isPresent)
//        .map(Optional::get)
//        .orElseThrow(() -> new PaybackException(GATEWAY_002, "getCashbackReward 실패"));
//
//    Optional<String> opt = Optional.empty(); // Optional를 null로 초기화함.
//    System.out.println(opt.orElse("빈 Optional 객체"));
//    System.out.println(opt.orElseGet(String::new));
//
//  }
}