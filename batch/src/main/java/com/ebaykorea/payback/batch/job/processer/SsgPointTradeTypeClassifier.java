package com.ebaykorea.payback.batch.job.processer;

import com.ebaykorea.payback.batch.domain.SsgPointProcesserDto;
import com.ebaykorea.payback.batch.domain.constant.PointTradeType;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

@AllArgsConstructor
public class SsgPointTradeTypeClassifier implements Classifier<SsgPointProcesserDto, ItemProcessor<?, ?>> {

  private ItemProcessor<SsgPointProcesserDto, SsgPointProcesserDto> earnProcesser;
  private ItemProcessor<SsgPointProcesserDto, SsgPointProcesserDto> cancelProcesser;

  @Override
  public ItemProcessor<?, ?> classify(final SsgPointProcesserDto classifiable) {
    if(classifiable.getTradeType() == PointTradeType.Save) {
      return earnProcesser;
    } else {
      return cancelProcesser;
    }
  }
}
