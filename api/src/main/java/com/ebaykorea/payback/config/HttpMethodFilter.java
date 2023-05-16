package com.ebaykorea.payback.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
public class HttpMethodFilter extends OncePerRequestFilter {
  private final Collection<String> FORBIDDEN_METHODS = List.of("TRACE", "OPTIONS", "HEAD", "DELETE", "PATCH");

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if (FORBIDDEN_METHODS.contains(request.getMethod())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    } else {
      filterChain.doFilter(request, response);
    }
  }
}
