package com.ebaykorea.payback.core.client.reward.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
