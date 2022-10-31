package com.ebaykorea.payback.infrastructure.persistence.mapper;

import com.ebaykorea.payback.core.domain.entity.cashback.Cashback;
import com.ebaykorea.payback.core.domain.entity.cashback.Cashbacks;
import com.ebaykorea.payback.core.domain.entity.cashback.member.Club;
import com.ebaykorea.payback.core.domain.entity.cashback.policy.Policy;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.entity.CashbackOrderDetailEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.entity.CashbackOrderEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.entity.CashbackOrderMemberEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.entity.CashbackOrderPolicyEntity;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CashbackEntityMapper {

  default Timestamp map(Instant instant) {
    return instant == null ? null : Timestamp.from(instant);
  }

  default String mapIsClub(Club club) {
    return Objects.isNull(club) ? "N" : "Y";
  }

  @Mapping(source = "cashback.orderNo", target = "buyOrderNo")
  @Mapping(source = "cashback.type", target = "cashbackType")
  @Mapping(source = "cashback.amount", target = "cashbackMoney")
  @Mapping(source = "cashback.basisAmount", target = "cashbackBasisMoney")
  @Mapping(source = "cashback.itemNo", target = "gdNo")
  @Mapping(source = "cashback.shopType", target = "shopType")
  @Mapping(source = "cashback.useEnableDate", target = "useEnableDt")
  @Mapping(source = "cashbacks.packNo", target = "packNo")
  @Mapping(source = "cashbacks.orderSiteType", target = "siteType")
  @Mapping(source = "cashbacks.member.buyerNo", target = "custNo")
  @Mapping(source = "cashbacks.member.userKey", target = "userKey")
  @Mapping(source = "cashbacks.member.smileClubMember", target = "smileClubYn")
  @Mapping(source = "cashbacks.member.buyerId", target = "regId")
  @Mapping(source = "cashbacks.orderDate", target = "regDt")
  @Mapping(constant = "SV", target = "tradeCd")
  @Mapping(constant = "10", target = "tradeStatus")
  CashbackOrderEntity map(Cashbacks cashbacks , Cashback cashback);

  @Mapping(source = "packNo", target = "packNo")
  @Mapping(source = "member.buyerId", target = "buyerNo")
  @Mapping(source = "member.club.partnerId", target = "regSite" , ignore = true)
  @Mapping(source = "member.club.payCycleType", target = "payType", ignore = true)
  @Mapping(source = "member.club.membershipGrade", target = "memberGrade", ignore = true)
  @Mapping(source = "member.club", target = "clubCheckYn")
  @Mapping(source = "member.buyerId", target = "insOprt")
  @Mapping(source = "orderDate", target = "insDate")
  @Mapping(source = "member.buyerId", target = "updOprt")
  @Mapping(source = "orderDate", target = "updDate")
  CashbackOrderMemberEntity mapToMember(Cashbacks cashbacks);


  CashbackOrderDetailEntity mapToDetail(Cashbacks cashbacks , Cashback cashback);
  CashbackOrderPolicyEntity mapToPolicy(Cashbacks cashbacks , Policy policy);

}
