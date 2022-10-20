package com.ebaykorea.payback.infrastructure.gateway.client.reward.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.Optional;

@Data
@Builder
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class RewardBaseResponse<T> {
    private final static String ResponseOK = "0000";

    private RewardBaseReturn returnBase;

    private T result;

    public Optional<T> findData() {
        return Optional.ofNullable(result);
    }
    public boolean isSuccess() { return returnBase != null && ResponseOK.equals(returnBase.getReturnCode()); }

}
