package com.ebaykorea.payback.core.gateway;

import java.util.Optional;

public interface UserGateway {
  Optional<String> findUserKey(String buyerNo);

  /**
   * userToken으로 G/A 고객번호/고객아이디를 조회합니다
   **/
  String getUserId(String userToken);
}
