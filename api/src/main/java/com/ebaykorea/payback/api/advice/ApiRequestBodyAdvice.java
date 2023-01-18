package com.ebaykorea.payback.api.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.opentracing.util.GlobalTracer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Profile;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

@Slf4j
@Profile("fusion")
@RestControllerAdvice
public class ApiRequestBodyAdvice extends RequestBodyAdviceAdapter {
  private static final String AFTER_READ_EXCEPTION = "afterBodyRead throws exception when trying to read body";
  private final ObjectMapper objectMapper;

  public ApiRequestBodyAdvice(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
    try {
      String jsonBody = objectMapper.writeValueAsString(body);
      final var span = GlobalTracer.get().activeSpan();

      MDC.put("payback.request.body", jsonBody);
      span.setTag("http.body", jsonBody);
    } catch (JsonProcessingException e) {
      log.error(AFTER_READ_EXCEPTION, e);
    }

    return body;
  }
}
