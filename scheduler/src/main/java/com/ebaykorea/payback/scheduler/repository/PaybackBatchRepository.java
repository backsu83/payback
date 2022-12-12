package com.ebaykorea.payback.scheduler.repository;


import com.ebaykorea.payback.scheduler.service.entity.PaybackBatchRecord;
import com.ebaykorea.payback.scheduler.service.entity.ProcessType;
import com.ebaykorea.payback.scheduler.service.mapper.PaybackBatchRecordMapper;
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
        .collect(Collectors.toList());
  }

  public void updateStatus(String orderKey, String txKey, ProcessType processType, Long retryCount) {
    cashbackOrderBatchRepository.updateSatus(orderKey ,txKey ,processType.name(), retryCount, updOprt);
  }
}
