package com.ebaykorea.payback.batch.job;

import static com.ebaykorea.payback.batch.util.PaybackDateTimes.DATE_TIME_FORMATTER;

import com.ebaykorea.payback.batch.domain.SsgPointProcesserDto;
import com.ebaykorea.payback.batch.job.processer.SsgPointCancelProcesser;
import com.ebaykorea.payback.batch.job.processer.SsgPointEarnProcesser;
import com.ebaykorea.payback.batch.job.processer.SsgPointJobListener;
import com.ebaykorea.payback.batch.job.processer.SsgPointProcesserMapper;
import com.ebaykorea.payback.batch.job.processer.SsgPointStepListener;
import com.ebaykorea.payback.batch.job.processer.SsgPointTradeTypeClassifier;
import com.ebaykorea.payback.batch.job.reader.QuerydslPagingItemReader;
import com.ebaykorea.payback.batch.job.writer.SsgPointTargetWriter;
import com.ebaykorea.payback.batch.repository.opayreward.SsgPointTargetRepositorySupport;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntity;
import com.ebaykorea.payback.batch.util.PaybackDateTimes;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
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
public class SsgPointProcessingJobConfig {

  public static final String JOB_NAME = "ssgPointTagerJob";
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final EntityManagerFactory entityManagerFactory;

  private final SsgPointTargetRepositorySupport ssgPointTargetRepositorySupport;
  private final SsgPointEarnProcesser ssgPointEarnProcesser;
  private final SsgPointCancelProcesser ssgPointCancelProcesser;
  private final SsgPointTargetWriter ssgPointTargetWriter;
  private final SsgPointProcesserMapper ssgPointProcesserMapper;
  private final SsgPointStepListener ssgPointStepListener;
  private final SsgPointJobListener ssgPointJobListener;

  private final int chunkSize = 5;

  @Bean
  public Job ssgpointTargetJob() {
    return jobBuilderFactory.get(JOB_NAME)
        .listener(ssgPointJobListener)
        .start(excuteAllowStep(null))
          .on("FAILED")
          .end()
        .from(excuteAllowStep(null))
          .on("*")
          .to(ssgPointTargetStep())
        .end()
        .build();
  }

  @Bean
  @JobScope
  public Step excuteAllowStep(@Value("#{jobParameters[reqTime]}") String reqDateTime) {
    return stepBuilderFactory.get("excuteAllowStep")
        .tasklet((contribution, chunkContext) -> {
          var reqTime = LocalTime.parse(reqDateTime , DATE_TIME_FORMATTER);
          if ( LocalTime.of(0,0).isAfter(reqTime)
              && LocalTime.of(8,0).isBefore(reqTime)) {
              contribution.setExitStatus(ExitStatus.FAILED);
          }
          return RepeatStatus.FINISHED;
        })
        .build();
  }

  @Bean
  public Step ssgPointTargetStep() {
    return stepBuilderFactory.get("ssgPointTargetStep")
        .listener(ssgPointStepListener)
        .<SsgPointTargetEntity, SsgPointTargetEntity>chunk(chunkSize)
        .reader(reader())
        .processor(compositeItemProcessor())
        .writer(ssgPointTargetWriter)
        .build();
  }

  @Bean
  public QuerydslPagingItemReader<SsgPointTargetEntity> reader() {
    return new QuerydslPagingItemReader<>(entityManagerFactory,
        chunkSize,
        queryFactory -> ssgPointTargetRepositorySupport.findByStatusReady());
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
  public ItemProcessor<SsgPointProcesserDto , SsgPointTargetEntity> classifierProcessor() {
    ClassifierCompositeItemProcessor<SsgPointProcesserDto, SsgPointTargetEntity> itemProcessor
        = new ClassifierCompositeItemProcessor<>();
    itemProcessor.setClassifier(classifier());
    return itemProcessor;
  }


}
