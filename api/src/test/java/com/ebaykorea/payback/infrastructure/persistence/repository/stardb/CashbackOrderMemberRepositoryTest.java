package com.ebaykorea.payback.infrastructure.persistence.repository.stardb;

import com.ebaykorea.payback.infrastructure.persistence.redis.support.GsonUtils;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.entity.CashbackOrderMemberEntity;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Disabled
class CashbackOrderMemberRepositoryTest {

  @Autowired
  CashbackOrderMemberRepository repository;


  @Test
  void findById() {
    Optional<CashbackOrderMemberEntity> byId = repository.findById(5227995641L);
    System.out.println(GsonUtils.toJsonPretty(byId.get()));

  }

  @Test
  void save() {
    Long id = 5227995641L;
    Optional<CashbackOrderMemberEntity> byId = repository.findById(id);
    if(!Objects.isNull(byId)) {
      final var nowDate = Timestamp.from(Instant.now());
      CashbackOrderMemberRepository result = repository.save(CashbackOrderMemberEntity.builder()
          .packNo(id)
          .buyerNo("132871942")
          .memberGrade("BASC")
          .payType("ANNL")
          .regSite("S002")
          .clubCheckYn("Y")
          .insDate(nowDate)
          .insOprt("test")
          .updDate(nowDate)
          .updOprt("test").build());
      System.out.println(GsonUtils.toJsonPretty(result));
    }
  }
}