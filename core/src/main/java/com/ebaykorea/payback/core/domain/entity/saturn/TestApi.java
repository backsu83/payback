package com.ebaykorea.payback.core.domain.entity.saturn;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TestApi {

    @JsonProperty("name")
    private String name;

    @JsonProperty("swagger")
    private String swagger;

    @JsonProperty("devUrl")
    private String devUrl;

    @JsonProperty("prodUrl")
    private String ProdUrl;
}
