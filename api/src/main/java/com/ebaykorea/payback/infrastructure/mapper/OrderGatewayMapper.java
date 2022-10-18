package com.ebaykorea.payback.infrastructure.mapper;

import com.ebaykorea.payback.core.domain.constant.MemberType;
import com.ebaykorea.payback.core.domain.entity.Buyer;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.order.OrderItem;
import com.ebaykorea.payback.infrastructure.gateway.client.order.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface OrderGatewayMapper {

  @Mapping(target = "buyer", expression = "java(mapToBuyer(source.getBuyer(), userKey))")
  @Mapping(source = "source.orderBase.orderDate", target = "orderDate")
  Order map(OrderQueryResponseDto source, String userKey);

  @Mapping(source = "source.memberType", target = "member", qualifiedByName = "mapIsMember")
  @Mapping(source = "source.smileClubMembership", target = "smileClubMember", qualifiedByName = "mapIsSmileClubMember")
  Buyer mapToBuyer(BuyerDto source, String userKey);

  @Named("mapIsMember")
  default boolean mapIsMember(final MemberType memberType) {
    return memberType == MemberType.NormalMember;
  }

  @Named("mapIsSmileClubMember")
  default boolean mapIsSmileClubMember(final BuyerDto.SmileClubMembershipDto smileClubMembership) {
    return Optional.ofNullable(smileClubMembership)
        .map(BuyerDto.SmileClubMembershipDto::isSmileClubMember)
        .orElse(false);
  }

  @Mapping(source = "snapshotKey", target = "itemSnapshotKey")
  @Mapping(source = "source.branch.branchPrice", target = "branchPrice")
  OrderItem map(OrderItemDto source);
}
