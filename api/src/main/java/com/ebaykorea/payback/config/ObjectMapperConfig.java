package com.ebaykorea.payback.config;

import com.ebaykorea.payback.util.PaybackDateTimeFormatters;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;


@Configuration
public class ObjectMapperConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        LocalDateTimeSerializer localDateTimeSerializer
                = new LocalDateTimeSerializer(PaybackDateTimeFormatters.DATE_TIME_FORMATTER);
        javaTimeModule.addSerializer(LocalDateTime.class, localDateTimeSerializer);
        objectMapper.registerModule(javaTimeModule);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        return objectMapper;
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(PaybackDateTimeFormatters.dateTimeFormat);
            builder.serializers(new LocalDateSerializer(PaybackDateTimeFormatters.DATE_TIME_FORMATTER));
            builder.serializers(new LocalDateTimeSerializer(PaybackDateTimeFormatters.DATE_FORMATTER));
        };
    }
}
