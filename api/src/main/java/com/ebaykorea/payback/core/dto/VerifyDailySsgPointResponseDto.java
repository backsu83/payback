package com.ebaykorea.payback.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerifyDailySsgPointResponseDto {
    @Schema(description = "대사일")
    @NotNull
    private String tradeDate;

    @Schema(description = "사이트타입")
    @NotNull
    private String siteType;

    @Schema(description = "적립/취소")
    @NotNull
    private String tradeType;

    @Schema(description = "개수")
    @NotNull
    private Long count;

    @Schema(description = "금액")
    @NotNull
    private BigDecimal amount;

    @Schema(description = "응답코드")
    @NotNull
    private String returnCode;

    @Schema(description = "응답메시지")
    @NotNull
    private String returnMessage;

    @Schema(description = "입력자")
    @NotNull
    private String InsertOperator;

    @Schema(description = "입력일")
    @NotNull
    private Instant InsertDate;

    @Schema(description = "수정자")
    @NotNull
    private String UpdateOperator;

    @Schema(description = "수정일")
    @NotNull
    private Instant UpdateDate;

}
