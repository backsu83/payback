package com.ebaykorea.payback.adapter.rest.dto.common;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final String status;
    private final String message;

    public ErrorResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

}
