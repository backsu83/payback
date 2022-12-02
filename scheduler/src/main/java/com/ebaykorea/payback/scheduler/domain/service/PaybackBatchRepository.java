package com.ebaykorea.payback.scheduler.domain.service;


import com.ebaykorea.payback.scheduler.domain.entity.PaybackBatchRecord;
import com.ebaykorea.payback.scheduler.domain.mapper.PaybackBatchRecordMapper;
import com.ebaykorea.payback.scheduler.infrastructure.repository.CashbackOrderBatchRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaybackBatchRepository {

  private final CashbackOrderBatchRepository cashbackOrderBatchRepository;
  private final PaybackBatchRecordMapper paybackBatchRecordMapper;

  public List<PaybackBatchRecord> findOrderKeyAndTxKey() {
    return cashbackOrderBatchRepository.findOrderkeyAndTxkey()
        .stream()
        .map(paybackBatchRecordMapper::map)
        .collect(Collectors.toList());
  }

  public void updateStatus() {
    //TODO cashback_order_batch 업데이트
    return;
  }
}
