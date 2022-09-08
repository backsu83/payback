package com.ebaykorea.payback.core.persistence.redis;

import com.ebaykorea.payback.core.persistence.redis.entity.RedisCustomEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisJpaRepository extends CrudRepository<RedisCustomEntity, String> {

}
