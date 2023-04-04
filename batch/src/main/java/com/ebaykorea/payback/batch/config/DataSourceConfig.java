//package com.ebaykorea.payback.batch.config;
//
//import com.ebaykorea.saturn.datasource.EnableSaturnDataSource;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Configuration;
//
///**
// * jenkins 환경에서 테스트 실행시 DCM 접근 오류이슈를 방지하기 위해
// * live 환경에서만 EnableSaturnDataSource을 사용하기 위한 처리
// */
//@Configuration
//@ConditionalOnProperty(prefix = "payback.dcm.access", name = "enable", havingValue = "true")
//public class DataSourceConfig {
//}
