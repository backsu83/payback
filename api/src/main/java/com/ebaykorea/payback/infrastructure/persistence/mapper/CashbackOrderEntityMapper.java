package com.ebaykorea.payback.infrastructure.persistence.mapper;

import com.ebaykorea.payback.core.domain.entity.cashback.Cashback;
import com.ebaykorea.payback.core.domain.entity.cashback.PayCashback;
import com.ebaykorea.payback.core.domain.entity.cashback.member.Club;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.CashbackUnit;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.entity.CashbackOrderDetailEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.entity.CashbackOrderEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.entity.CashbackOrderMemberEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ebaykorea.payback.util.PaybackBooleans;
import com.ebaykorea.payback.util.PaybackTimestamps;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = {PaybackTimestamps.class, PaybackBooleans.class}
)
public interface CashbackOrderEntityMapper {

  default String mapIsClub(Club club) {
    return Objects.isNull(club) ? "N" : "Y";
  }


  default List<CashbackOrderEntity> map(final PayCashback payCashback, final Cashback cashback, final List<CashbackUnit> cashbackUnits) {
    return cashbackUnits.stream()
        .map(cashbackUnit -> map(payCashback, cashback, cashbackUnit))
        .collect(Collectors.toUnmodifiableList());
  }

  @Mapping(source = "cashbackUnit.type.dbCode", target = "cashbackType")
  @Mapping(source = "payCashback.member.buyerNo", target = "buyerNo")
  @Mapping(source = "payCashback.member.userKey", target = "userKey")
  @Mapping(expression = "java(PaybackTimestamps.from(cashbackUnit.getUseEnableDate()))", target = "useEnableDt")
  @Mapping(expression = "java(PaybackBooleans.toYN(payCashback.getMember().isSmileClubMember()))", target = "smileClubYn")
  @Mapping(source = "cashbackUnit.shopType.code", target = "shopType")
  @Mapping(source = "payCashback.member.buyerNo", target = "regId")
  @Mapping(expression = "java(PaybackTimestamps.from(payCashback.getOrderDate()))", target = "regDt")
  @Mapping(source = "payCashback.member.buyerNo", target = "chgId")
  @Mapping(constant = "SV", target = "tradeCd")
  @Mapping(constant = "10", target = "tradeStatus")
  @Mapping(constant = "G", target = "siteType")
  CashbackOrderEntity map(PayCashback payCashback, Cashback cashback, CashbackUnit cashbackUnit);

  //TODO
//  @Mapping(source = "packNo", target = "packNo")
//  @Mapping(source = "member.buyerNo", target = "buyerNo")
//  @Mapping(source = "member.club.partnerId", target = "regSite" , ignore = true)
//  @Mapping(source = "member.club.payCycleType", target = "payType", ignore = true)
//  @Mapping(source = "member.club.membershipGrade", target = "memberGrade", ignore = true)
//  @Mapping(source = "member.club", target = "clubCheckYn")
//  @Mapping(source = "member.buyerNo", target = "insOprt")
//  @Mapping(source = "orderDate", target = "insDate")
//  @Mapping(source = "member.buyerNo", target = "updOprt")
//  @Mapping(source = "orderDate", target = "updDate")
//  CashbackOrderMemberEntity mapToMember(PayCashback payCashback);


  //CashbackOrderDetailEntity mapToDetail(PayCashback payCashback , Cashback cashback);


}
