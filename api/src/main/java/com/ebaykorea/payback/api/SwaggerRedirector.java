package com.ebaykorea.payback.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/")
class SwaggerRedirector {
    @ApiIgnore
    @GetMapping
    public String api() { return "redirect:/swagger-ui.html"; }
}