package com.ebaykorea.payback.config;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("dispatcherServlet")
public class DispatcherServletConfig extends DispatcherServlet {
  /**
   * HttpServlet.doTrace를 실행하지 않기 위해
   * FrameworkServlet.doTrace를 override하여 processRequest만 수행한다.
   */
  @Override
  protected void doTrace(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // 허용하는 메서드만 Allow Header에 전달
    response.setHeader(
        "Allow",
        String.join(", ", HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name())
    );

    processRequest(request, response);
  }
}
