package com.ebaykorea.payback.batch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SsgPointRecoverSkipPolicy implements SkipPolicy {

  @Override
  public boolean shouldSkip(final Throwable t, final int skipCount)
      throws SkipLimitExceededException {
    return true;
  }
}
