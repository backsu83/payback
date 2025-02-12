package com.ebaykorea.payback.schedulercluster.grocery

import com.ebaykorea.payback.schedulercluster.client.dto.member.QuiltBaseResponse


class QuiltApiGrocery {
  static def QuiltBaseResponse_생성(Map map = [:]) {
    new QuiltBaseResponse<String>(
        (map.resultCode ?: 23268003L) as int,
        (map.message ?: "") as String,
        (map.data ?: "")  as String
    )
  }
}
