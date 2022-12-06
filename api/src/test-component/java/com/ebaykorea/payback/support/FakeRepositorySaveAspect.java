package com.ebaykorea.payback.support;


import com.ebaykorea.payback.infrastructure.persistence.repository.PaybackSqlRepository;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


/**
 * {@link PaybackSqlRepository}
 * repository 구현체들을 이용하여 DB에 저장하는 save method를 wrapping 합니다
 */
@Aspect
@Component
public class FakeRepositorySaveAspect {
  /**
   * repository 내 save 메서드가 실행 되면, save 구현체를 실행하지 않고 리턴합니다.
   */
  @Around("execution(* com.ebaykorea.payback.infrastructure.persistence.repository.PaybackSqlRepository.save(*))")
  public void process() {

  }
}
