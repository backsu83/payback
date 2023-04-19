package com.ebaykorea.payback.batch.job.mapper;

import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointVerifyRequest;
import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointVerifyResponse;
import com.ebaykorea.payback.batch.domain.SsgPointVerifyDto;
import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import com.ebaykorea.payback.batch.domain.constant.PointStatusType;
import com.ebaykorea.payback.batch.domain.constant.PointTradeType;
import com.ebaykorea.payback.batch.util.PaybackDecimals;
import com.ebaykorea.payback.batch.util.PaybackInstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {
                OrderSiteType.class,
                PointStatusType.class,
                PointTradeType.class,
                BigDecimal.class,
                PaybackInstants.class,
                PaybackDecimals.class
        }
)
public interface SsgPointVerifyProcesserMapper {
    @Mapping(source = "request.reqDate", target = "tradeDate")
    @Mapping(source = "siteType", target = "siteType")
    @Mapping(source = "tradeType", target = "tradeType")
    @Mapping(source = "request.sumCount", target = "count")
    @Mapping(source = "request.sumAmt", target = "amount")
    @Mapping(source = "response.responseCd", target = "returnCode")
    @Mapping(source = "response.responseMsg", target = "returnMessage")
    SsgPointVerifyDto mapToVerify(SsgPointVerifyRequest request, SsgPointVerifyResponse response, String siteType, String tradeType);
}
