package com.ebaykorea.payback.config;

import com.ebaykorea.payback.config.properties.ApiInfoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private ApiInfoProperties apiInfoProperties;

    @Autowired
    public SwaggerConfig(ApiInfoProperties apiInfoProperties) {
        this.apiInfoProperties = apiInfoProperties;
    }

    @Bean
    public Docket SwaggerApi() {
        String apiName = apiInfoProperties.getName();
        String apiVersion = apiInfoProperties.getVersion().getFull();
        String apiDescription = String.format("Documentation %s %s", apiName, apiVersion);

        return new Docket(DocumentationType.SWAGGER_2)
            .groupName(apiName)
            .apiInfo(
                new ApiInfoBuilder()
                    .version(apiVersion)
                    .title(apiName)
                    .description(apiDescription)
                    .build()
            )
            .select()
            .apis(RequestHandlerSelectors.basePackage(apiInfoProperties.getApiPackage()))
            .paths(PathSelectors.regex(".*?"))
            .build();
    }
}
