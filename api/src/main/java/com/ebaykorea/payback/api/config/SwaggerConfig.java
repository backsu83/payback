package com.ebaykorea.payback.api.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SwaggerConfig {
    private static final String API_NAME = "Payback API";
    private static final String API_VERSION = "1.0.0";
    private static final String API_DESCRIPTION = "페이백 API";

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title(API_NAME)
                .version(API_VERSION)
                .description(API_DESCRIPTION);

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
