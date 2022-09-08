package com.ebaykorea.payback.core.domain.entity.saturn;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TestApiGroup {

    @JsonProperty("name")
    private String name = "name";

    @JsonProperty("title")
    private String title = "test";

    @JsonProperty("api")
    private List<TestApi> api;

}
