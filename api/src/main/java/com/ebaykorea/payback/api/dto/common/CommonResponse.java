package com.ebaykorea.payback.api.dto.common;

import com.ebaykorea.payback.core.domain.constant.PaybackMessageType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {

    private int code;
    private T data;
    private String message;

    public CommonResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public CommonResponse(HttpStatus status , T data) {
        this.code = status.value();
        this.data = data;
    }

    public CommonResponse(T data) {
        this.data = data;
    }

    public CommonResponse(String message) {
        this.message = message;
    }

    public CommonResponse(HttpStatus httpStatus) {
        this.message = httpStatus.name();
    }

    public static CommonResponse success(PaybackMessageType paybackMessageType) {
        return new CommonResponse(paybackMessageType.getMessage());
    }

    public static CommonResponse success() {
        return new CommonResponse(HttpStatus.OK);
    }

    public static CommonResponse create() {
        return new CommonResponse(HttpStatus.CREATED);
    }

}
