package com.ebaykorea.payback.api.controller;

import com.ebaykorea.payback.api.common.annotation.LogExcutor;
import com.ebaykorea.payback.core.domain.entity.saturn.TestApiGroup;
import com.ebaykorea.payback.core.domain.entity.saturn.TestServerInfo;
import com.ebaykorea.payback.core.domain.service.saturn.TestApiService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "테스트", description = "테스트 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestApiController {

    private final TestApiService testApiService;
    @LogExcutor
    @GetMapping("/test/openFeignClient")
    public ResponseEntity<List<TestApiGroup>> getApiGroupList() {
        return ResponseEntity.ok(testApiService.getApiGroupList());
    }
    @LogExcutor
    @GetMapping("/test/sqlServerInfo")
    public ResponseEntity<TestServerInfo> getServerVersion() {
        var serverInfo = testApiService.getServerInfo();
        return ResponseEntity.ok(serverInfo);
    }

    @LogExcutor
    @GetMapping("/test/service/exception")
    public void serviceException() {
        log.trace("==== trace ====");
        log.debug("==== debug ====");
        log.info("==== info ====");
        log.warn("==== warn ====");
        log.error("==== error ====");
        testApiService.serviceExceptionMethod();
    }

}
