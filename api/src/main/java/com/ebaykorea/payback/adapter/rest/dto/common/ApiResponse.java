package com.ebaykorea.payback.adapter.rest.dto.common;

public class ApiResponse<T> {

    public static <T> ApiResponseDto<T> success(T response) {
        if(response == null) {
            response = (T) String.valueOf("");
        }
        return new ApiResponseDto<>(true, response, null);
    }

    public static ApiResponseDto<?> error(ErrorResponse errorResponse){
        return new ApiResponseDto<>(false, null, errorResponse);
    }
}
