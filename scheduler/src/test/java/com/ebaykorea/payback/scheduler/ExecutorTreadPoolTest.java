package com.ebaykorea.payback.scheduler;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ExecutorTreadPoolTest {

  // 100ms 만큼 block되는 작업
  private Runnable getTask() {
    return () -> {
      try {
        TimeUnit.MILLISECONDS.sleep(100);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    };
  }

  // threadPool를 종료하고 모든 작업이 끝날 때까지 기다린다.
  private void shutDownTerminated(ExecutorService executorService) {
    try {
      executorService.shutdown();
      executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void testThreadPoolTasks() throws Exception {
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
        10,
        10,
        10,
        TimeUnit.SECONDS,
        new LinkedBlockingDeque<>(100));

    IntStream.range(0,100).forEach(i -> threadPoolExecutor.execute(getTask()));

    int poolSize = threadPoolExecutor.getPoolSize();
    int queueSize = threadPoolExecutor.getQueue().size();

    assertThat(poolSize).isEqualTo(10);
    assertThat(queueSize).isEqualTo(90);

    shutDownTerminated(threadPoolExecutor);
  }

}
