package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper;

import com.ebaykorea.payback.core.dto.member.MemberCashbackRequestDto;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SmileCashEventEntityMapper {

  default List<SmileCashEventEntity> map(final String memberKey, final List<MemberCashbackRequestDto> requests) {
    return requests.stream()
        .map(request -> map(memberKey, request))
        .collect(Collectors.toUnmodifiableList());
  }

  @Mapping(source = "request.payNo", target = "packNo")
  @Mapping(constant = "L", target = "commnType")
  @Mapping(constant = "SV", target = "tradeCode")
  @Mapping(constant = "A003", target = "smileCashCode")
  @Mapping(source = "request.saveAmount", target = "requestMoney")
  @Mapping(source = "request.saveAmount", target = "requestOutputDisabledMoney")
  @Mapping(constant = "G3", target = "cashBalanceType")//TODO
  @Mapping(source = "memberKey", target = "custNo")
  //@Mapping(constant = "0", target = "refNo") //TODO
  //@Mapping(constant = "0", target = "ersNo") //TODO
  @Mapping(source = "memberKey", target = "regId")
  SmileCashEventEntity map(String memberKey, MemberCashbackRequestDto request);
}
