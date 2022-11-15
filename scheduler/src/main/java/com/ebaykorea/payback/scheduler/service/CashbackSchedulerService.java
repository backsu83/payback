package com.ebaykorea.payback.scheduler.service;

import com.ebaykorea.payback.scheduler.client.PaybackApiClient;
import com.ebaykorea.payback.scheduler.client.PaybackRequestDto;
import com.ebaykorea.payback.scheduler.service.dto.CashbackCheckerDto;
import com.ebaykorea.payback.scheduler.service.mapper.CashbackCheckerMapper;
import com.ebaykorea.payback.scheduler.infrastructure.repository.CashbackCheckerRepository;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashbackSchedulerService {

  private final CashbackCheckerRepository cashbackCheckerRepository;
  private final CashbackCheckerMapper cashbackCheckerMapper;
  private final PaybackApiClient paybackApiClient;

  public void saveCashback() {
    final var list = findNoSaveCashbackKeys();
    list.stream()
        .filter(Objects::nonNull)
        .forEach(keys -> {
          paybackApiClient.saveCashbacks(PaybackRequestDto.builder()
              .orderKey(keys.getOrderKey())
              .txKey(keys.getTxKey())
              .build());
        });
  }

  public List<CashbackCheckerDto> findNoSaveCashbackKeys() {
    return cashbackCheckerRepository.findOrderkeyAndTxkey()
        .stream()
        .filter(entity -> StringUtils.isNotEmpty(entity.getOrderKey()) && StringUtils.isNotEmpty(entity.getTxKey()))
        .map(cashbackCheckerMapper::map)
        .collect(Collectors.toList());
  }

}
