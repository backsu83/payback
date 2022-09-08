package com.ebaykorea.payback.core.domain.service.saturn;

import com.ebaykorea.payback.core.client.reward.TestApiClient;
import com.ebaykorea.payback.core.common.exception.RedisCrudException;
import com.ebaykorea.payback.core.domain.entity.saturn.TestApiGroup;
import com.ebaykorea.payback.core.domain.entity.saturn.TestServerInfo;
import com.ebaykorea.payback.core.persistence.mssql.tiger.TestServerInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestApiService {

    private final TestApiClient testApiClient;
    private final TestServerInfoRepository serverInfoRepository;

    public List<TestApiGroup> getApiGroupList() {
        return testApiClient.apiGroupList();
    }

    public TestServerInfo getServerInfo() {
        return serverInfoRepository.getServerInfo(2);
    }

    public void serviceExceptionMethod() {
        throw new RedisCrudException("doamin error");
    }

}
