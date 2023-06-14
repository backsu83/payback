package com.ebaykorea.payback.batch.job.processor;

import com.ebaykorea.payback.batch.domain.SsgPointProcessorDto;
import com.ebaykorea.payback.batch.domain.SsgPointTargetDto;
import com.ebaykorea.payback.batch.domain.constant.PointTradeType;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

@AllArgsConstructor
public class SsgPointTradeTypeClassifier implements Classifier<SsgPointProcessorDto, ItemProcessor<?, ?>> {

  private ItemProcessor<SsgPointProcessorDto, SsgPointTargetDto> earnProcessor;
  private ItemProcessor<SsgPointProcessorDto, SsgPointTargetDto> cancelProcessor;

  @Override
  public ItemProcessor<?, ?> classify(final SsgPointProcessorDto classifiable) {
    if(classifiable.getTradeType() == PointTradeType.Save) {
      return earnProcessor;
    } else {
      return cancelProcessor;
    }
  }
}
