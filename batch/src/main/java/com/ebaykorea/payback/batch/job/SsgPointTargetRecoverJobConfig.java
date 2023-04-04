package com.ebaykorea.payback.batch.job;

import static com.ebaykorea.payback.batch.util.PaybackDateTimes.DATE_TIME_FORMATTER;

import com.ebaykorea.payback.batch.config.ThreadExecutorConfig;
import com.ebaykorea.payback.batch.domain.SsgPointProcesserDto;
import com.ebaykorea.payback.batch.domain.SsgPointTargetDto;
import com.ebaykorea.payback.batch.job.listener.SsgPointProcesserListener;
import com.ebaykorea.payback.batch.job.listener.SsgPointStepListener;
import com.ebaykorea.payback.batch.job.mapper.SsgPointProcesserMapper;
import com.ebaykorea.payback.batch.job.processer.SsgPointCancelProcesser;
import com.ebaykorea.payback.batch.job.processer.SsgPointEarnProcesser;
import com.ebaykorea.payback.batch.job.processer.SsgPointTradeTypeClassifier;
import com.ebaykorea.payback.batch.job.reader.SsgPointTargetReader;
import com.ebaykorea.payback.batch.job.reader.SsgPointTargetRecoverReader;
import com.ebaykorea.payback.batch.job.writer.SsgPointTargetRecoverWriter;
import com.ebaykorea.payback.batch.job.writer.SsgPointTargetWriter;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntity;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SsgPointTargetRecoverJobConfig {

  public static final String JOB_NAME = "ssgPointTagerRecoverJob";
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  private final SsgPointEarnProcesser ssgPointEarnProcesser;
  private final SsgPointCancelProcesser ssgPointCancelProcesser;
  private final SsgPointTargetRecoverWriter ssgPointTargetRecoverWriter;
  private final SsgPointProcesserMapper ssgPointProcesserMapper;
  private final SsgPointStepListener ssgPointStepListener;
  private final SsgPointTargetRecoverReader ssgPointTargetRecoverReader;
  private final SsgPointRecoverSkipPolicy ssgPointRecoverSkipPolicy;

  @Value("${ssgpoint.batch.chunkSize}")
  private int chunkSize;

  @Bean
  public Job ssgpointTargetRecoverJob() {
    return jobBuilderFactory.get(JOB_NAME)
        .start(excuteAllowRecoverStep(null))
          .on("FAILED")
          .end()
        .from(excuteAllowRecoverStep(null))
          .on("*")
          .to(ssgPointTargetRecoverStep())
        .end()
        .build();
  }

  @Bean
  @JobScope
  public Step excuteAllowRecoverStep(@Value("#{jobParameters[recoverTime]}") String reqDateTime) {
    return stepBuilderFactory.get("excuteAllowRecoverStep")
        .tasklet((contribution, chunkContext) -> {
          var reqTime = LocalTime.parse(reqDateTime , DATE_TIME_FORMATTER).getHour();
          if ( reqTime >= 0 && reqTime <= 9) {
            contribution.setExitStatus(ExitStatus.FAILED);
          }
          return RepeatStatus.FINISHED;
        })
        .build();
  }

  @Bean
  public Step ssgPointTargetRecoverStep() {
    return stepBuilderFactory.get("ssgPointTargetRecoverStep")
        .listener(ssgPointStepListener)
        .<SsgPointTargetEntity, SsgPointTargetDto>chunk(chunkSize)
        .reader(ssgPointTargetRecoverReader.queryDslReader())
        .processor(compositeItemProcessor())
        .writer(ssgPointTargetRecoverWriter)
        .faultTolerant()
        .skipPolicy(ssgPointRecoverSkipPolicy)
        .build();
  }


  @Bean
  public Classifier classifier() {
    return new SsgPointTradeTypeClassifier(ssgPointEarnProcesser , ssgPointCancelProcesser);
  }

  @Bean
  public CompositeItemProcessor compositeItemProcessor() {
    final var processor = new CompositeItemProcessor<>();
    List<ItemProcessor<?, ?>> delegates = new ArrayList<>();
    delegates.add(mapperProcesser());
    delegates.add(classifierProcessor());
    processor.setDelegates(delegates);
    return processor;
  }

  @Bean
  public ItemProcessor<SsgPointTargetEntity , SsgPointProcesserDto> mapperProcesser() {
    return item -> ssgPointProcesserMapper.map(item);
  }

  @Bean
  public ItemProcessor<SsgPointProcesserDto , SsgPointTargetDto> classifierProcessor() {
    ClassifierCompositeItemProcessor<SsgPointProcesserDto, SsgPointTargetDto> itemProcessor
        = new ClassifierCompositeItemProcessor<>();
    itemProcessor.setClassifier(classifier());
    return itemProcessor;
  }
}
