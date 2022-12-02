package com.ebaykorea.payback.api.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse {

    private int code;
    private Object data;
    private String message;

    public CommonResponse(int code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public CommonResponse(HttpStatus status , Object data) {
        this.code = status.value();
        this.data = data;
    }

    public CommonResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public CommonResponse(String message) {
        this.message = message;
    }

    public CommonResponse(HttpStatus httpStatus) {
        this.message = httpStatus.name();
    }

    public static CommonResponse success(ResponseMessageType responseMessageType) {
        return new CommonResponse(responseMessageType.name());
    }

    public static CommonResponse success(ResponseMessageType responseMessageType, Object body) {
        return new CommonResponse(responseMessageType.name() , body);
    }

    public static CommonResponse success() {
        return new CommonResponse(HttpStatus.OK);
    }

    public static CommonResponse create() {
        return new CommonResponse(HttpStatus.CREATED);
    }

}
