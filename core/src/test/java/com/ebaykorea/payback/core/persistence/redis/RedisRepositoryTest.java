package com.ebaykorea.payback.core.persistence.redis;

import com.ebaykorea.payback.core.common.exception.RedisCrudException;
import com.ebaykorea.payback.core.common.support.GsonUtils;
import com.ebaykorea.payback.core.persistence.redis.entity.RedisCustomEntity;
import com.ebaykorea.payback.core.persistence.stardb.reward.DatabaseConfigTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest(classes = DatabaseConfigTest.class)
public class RedisRepositoryTest {

    @Autowired
    RedisRepository repository;

    @Test
    void findByKey() {
        RedisCustomEntity byKey = repository.findKey("10005");
        System.out.println(byKey.getRefreshTime());
        System.out.println(GsonUtils.toJson(byKey));
    }

    @Test
    void save() {
        RedisCustomEntity entity = RedisCustomEntity
                .builder()
                .id("10006")
                .amount(1000L)
                .refreshTime(LocalDateTime.now())
                .build();
        repository.save(entity);
    }

    @Test()
    void remove() {
        repository.remove("10002");
        Assertions.assertThrows(RedisCrudException.class , () ->{
            repository.findKey("10002");
        });
    }

    @Test
    void update() {
        RedisCustomEntity entity = RedisCustomEntity
                .builder()
                .id("10000")
                .amount(11111L)
                .refreshTime(LocalDateTime.now())
                .build();
        repository.update(entity);
    }
}