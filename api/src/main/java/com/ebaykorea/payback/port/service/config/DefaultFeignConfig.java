package com.ebaykorea.payback.port.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.codec.Decoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

/**
 * feign client 로그 레벨 변경 ( debug -> info )
 * objectMapper 적용
 */
public class DefaultFeignConfig {

    @Autowired
    private ObjectMapper objectMapper;
    @Bean
    public FeignRequestLogging customFeignRequestLogging() {
        return new FeignRequestLogging();
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

    @Bean
    public ObjectMapper insensitiveMapper() {
        return objectMapper;
    }

    @Bean
    public Decoder feignDecoder(
            @Qualifier("insensitiveMapper") ObjectMapper mapper
    ) {
        return new NullSafeDecoder(new JacksonDecoder(mapper));
    }

}
