package com.ebaykorea.payback.batch.job;

import com.ebaykorea.payback.batch.domain.SsgPointProcesserDto;
import com.ebaykorea.payback.batch.job.processer.SsgPointCancelProcesser;
import com.ebaykorea.payback.batch.job.processer.SsgPointEarnProcesser;
import com.ebaykorea.payback.batch.job.processer.SsgPointProcesserMapper;
import com.ebaykorea.payback.batch.job.processer.SsgPointTradeTypeClassifier;
import com.ebaykorea.payback.batch.job.reader.QuerydslPagingItemReader;
import com.ebaykorea.payback.batch.job.writer.SsgPointTargetWriter;
import com.ebaykorea.payback.batch.repository.opayreward.SsgPointTargetRepositorySupport;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SsgPointProcessingJobConfig {

  public static final String JOB_NAME = "SSG_POINT_PROCESSER";
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final EntityManagerFactory entityManagerFactory;

  private final SsgPointTargetRepositorySupport ssgPointTargetRepositorySupport;
  private final SsgPointEarnProcesser ssgPointEarnProcesser;
  private final SsgPointCancelProcesser ssgPointCancelProcesser;
  private final SsgPointTargetWriter ssgPointTargetWriter;
  private final SsgPointProcesserMapper ssgPointProcesserMapper;

  private final int chunkSize = 10;

  @Bean
  public Job ssgpointJob() {
    return jobBuilderFactory.get(JOB_NAME)
        .start(ssgpointStep())
        .build();
  }

  @Bean
  public Step ssgpointStep() {
    return stepBuilderFactory.get("ssgpointStep")
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
