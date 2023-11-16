package com.ebaykorea.payback.api.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SsgPointResponse<T> {

    private String returnCode;
    private String returnMessage;
    private T data;

    public SsgPointResponse(String returnCode, String returnMessage, T data) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.data = data;
    }
}
