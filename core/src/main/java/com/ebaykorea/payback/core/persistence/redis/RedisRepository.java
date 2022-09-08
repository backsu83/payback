package com.ebaykorea.payback.core.persistence.redis;

import com.ebaykorea.payback.core.persistence.redis.entity.RedisCustomEntity;

public interface RedisRepository {

    RedisCustomEntity findKey(String key);
    void save(RedisCustomEntity redisCmd);
    void remove(String key);
    RedisCustomEntity update(RedisCustomEntity redisCmd);
}
