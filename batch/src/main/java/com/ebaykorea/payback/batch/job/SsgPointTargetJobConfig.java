package com.ebaykorea.payback.batch.job;

import com.ebaykorea.payback.batch.config.ThreadExecutorConfig;
import com.ebaykorea.payback.batch.domain.SsgPointProcessorDto;
import com.ebaykorea.payback.batch.domain.SsgPointTargetDto;
import com.ebaykorea.payback.batch.job.listener.SsgPointProcessorListener;
import com.ebaykorea.payback.batch.job.listener.SsgPointStepListener;
import com.ebaykorea.payback.batch.job.mapper.SsgPointProcessorMapper;
import com.ebaykorea.payback.batch.job.processor.SsgPointCancelProcessor;
import com.ebaykorea.payback.batch.job.processor.SsgPointEarnProcessor;
import com.ebaykorea.payback.batch.job.processor.SsgPointTradeTypeClassifier;
import com.ebaykorea.payback.batch.job.reader.SsgPointTargetReader;
import com.ebaykorea.payback.batch.job.writer.SsgPointTargetWriter;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntity;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SsgPointTargetJobConfig {

  public static final String JOB_NAME = "ssgPointTargetJob";
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final ThreadExecutorConfig threadExecutorConfig;

  private final SsgPointEarnProcessor ssgPointEarnProcessor;
  private final SsgPointCancelProcessor ssgPointCancelProcessor;
  private final SsgPointTargetWriter ssgPointTargetWriter;
  private final SsgPointProcessorMapper ssgPointProcessorMapper;
  private final SsgPointStepListener ssgPointStepListener;
  private final SsgPointProcessorListener ssgPointProcessorListener;
  private final SsgPointTargetReader ssgPointTargetReader;

  @Value("${ssgpoint.batch.chunkSize}")
  private int chunkSize;

  @Bean
  public Job ssgpointTargetJob() {
    return jobBuilderFactory.get(JOB_NAME)
        .start(ssgPointEarnStep())
        .next(ssgPointCancelStep())
        .build();
  }

  @Bean
  public Step ssgPointEarnStep() {
    return stepBuilderFactory.get("ssgPointEarnStep")
        .listener(ssgPointStepListener)
        .<SsgPointTargetEntity, SsgPointTargetDto>chunk(chunkSize)
        .reader(ssgPointTargetReader.earnReader())
        .processor(compositeItemProcessor())
        .writer(ssgPointTargetWriter)
        .listener(ssgPointProcessorListener)
        .faultTolerant()
        .skip(Exception.class)
        .skipLimit(5000)
        .taskExecutor(threadExecutorConfig.taskExecutor())
        .throttleLimit(10)
        .build();
  }

  @Bean
  public Step ssgPointCancelStep() {
    return stepBuilderFactory.get("ssgPointCancelStep")
        .listener(ssgPointStepListener)
        .<SsgPointTargetEntity, SsgPointTargetDto>chunk(chunkSize)
        .reader(ssgPointTargetReader.cancelReader())
        .processor(compositeItemProcessor())
        .writer(ssgPointTargetWriter)
        .listener(ssgPointProcessorListener)
        .faultTolerant()
        .skip(Exception.class)
        .skipLimit(5000)
        .taskExecutor(threadExecutorConfig.taskExecutor())
        .throttleLimit(10)
        .build();
  }

  public Classifier classifier() {
    return new SsgPointTradeTypeClassifier(ssgPointEarnProcessor , ssgPointCancelProcessor);
  }

  public CompositeItemProcessor compositeItemProcessor() {
    final var processor = new CompositeItemProcessor<>();
    List<ItemProcessor<?, ?>> delegates = new ArrayList<>();
    delegates.add(mapperProcessor());
    delegates.add(classifierProcessor());
    processor.setDelegates(delegates);
    return processor;
  }

  public ItemProcessor<SsgPointTargetEntity , SsgPointProcessorDto> mapperProcessor() {
    return item -> ssgPointProcessorMapper.map(item);
  }

  public ItemProcessor<SsgPointProcessorDto , SsgPointTargetDto> classifierProcessor() {
    ClassifierCompositeItemProcessor<SsgPointProcessorDto, SsgPointTargetDto> itemProcessor
        = new ClassifierCompositeItemProcessor<>();
    itemProcessor.setClassifier(classifier());
    return itemProcessor;
  }
}
