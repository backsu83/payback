package com.ebaykorea.payback.scheduler.domain.service;


import com.ebaykorea.payback.scheduler.domain.entity.PaybackBatchRecord;
import com.ebaykorea.payback.scheduler.domain.entity.ProcessType;
import com.ebaykorea.payback.scheduler.domain.mapper.PaybackBatchRecordMapper;
import com.ebaykorea.payback.scheduler.infrastructure.repository.CashbackOrderBatchRepository;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaybackBatchRepository {

  public static final String updOprt = "paybackScheduler";

  private final CashbackOrderBatchRepository cashbackOrderBatchRepository;
  private final PaybackBatchRecordMapper paybackBatchRecordMapper;

  public List<PaybackBatchRecord> getRecords() {
    return cashbackOrderBatchRepository.findNoCompleted()
        .stream()
        .filter(f-> Objects.nonNull(f))
        .map(paybackBatchRecordMapper::map)
        .limit(1000)
        .collect(Collectors.toList());
  }

  public void updateStatus(String orderKey, String txKey, ProcessType processType, Long retryCount) {
    cashbackOrderBatchRepository.updateSatus(orderKey ,txKey ,processType.name(), retryCount, updOprt);
  }
}
