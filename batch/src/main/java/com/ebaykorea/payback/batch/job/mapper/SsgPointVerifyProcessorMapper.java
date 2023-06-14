package com.ebaykorea.payback.batch.job.mapper;

import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointVerifyRequest;
import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointVerifyResponse;
import com.ebaykorea.payback.batch.domain.SsgPointVerifyDto;
import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import com.ebaykorea.payback.batch.domain.constant.PointTradeType;
import com.ebaykorea.payback.batch.domain.constant.VerifyTradeType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SsgPointVerifyProcessorMapper {
    @Mapping(source = "request.reqDate", target = "tradeDate")
    @Mapping(source = "siteType", target = "siteType")
    @Mapping(source = "tradeType", target = "tradeType")
    @Mapping(source = "request.sumCount", target = "count")
    @Mapping(source = "request.sumAmt", target = "amount")
    @Mapping(source = "response.responseCd", target = "returnCode")
    @Mapping(source = "response.responseMsg", target = "returnMessage")
    SsgPointVerifyDto mapToVerify(SsgPointVerifyRequest request, SsgPointVerifyResponse response, OrderSiteType siteType, VerifyTradeType tradeType);
}
