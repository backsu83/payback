package com.ebaykorea.payback.adapter.persistence.redis.support;

public class RedisCrudException extends RuntimeException {
    public RedisCrudException(String message) {
        super(message);
    }
}
