package com.ebaykorea.payback.core;


import com.ebaykorea.payback.core.domain.entity.smilepoint.SmilePointTrade;
import com.ebaykorea.payback.core.repository.SmilePointTradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmilePointApplicationService {
  private final SmilePointTradeRepository smilePointTradeRepository;

  public long setSmilePoint(String custNo,
                            int point,
                            int reasonCode,
                            long contrNo,
                            String comment,
                            int ersNo,
                            int eId,
                            int apprStaus,
                            int targetType,
                            long winNo,
                            String sellerId) {
    return smilePointTradeRepository.setSmilePoint(custNo,
    point,
    reasonCode,
    contrNo,
    comment,
    ersNo,
    eId,
    apprStaus,
    targetType,
    winNo,
    sellerId);
  }

  public SmilePointTrade SelectSmilePointTradeBySmilePayNo(long smilePayNo) {
    var result = smilePointTradeRepository.SelectSmilePointTradeBySmilePayNo(smilePayNo);
    if (result == null) {
      return null;
    }
    SmilePointTrade smilePointTrade = new SmilePointTrade();
    smilePointTrade.setComment(result.getComment());
    smilePointTrade.setPoint(result.getPoint());
    smilePointTrade.setApprStatus(result.getApprStatus());
    smilePointTrade.setBuyerNo(result.getCustNo());
    smilePointTrade.setContrNo(result.getContrNo());
    smilePointTrade.setErrorMsg(result.getErrorMassage());
    smilePointTrade.setExpireDate(result.getReturnExpireDate());
    smilePointTrade.setRegDate(result.getRegDt());
    smilePointTrade.setReasonCode(result.getReasonCode());
    smilePointTrade.setSmilePayNo(result.getSmilePayNo());
    smilePointTrade.setTargetType(result.getTargetType());
    smilePointTrade.setSaveType(result.getServiceType());
    smilePointTrade.setCertApprId(result.getCertApprId());
    smilePointTrade.setUserKey(result.getUserKey());
    return smilePointTrade;
  }

  public List<SmilePointTrade> SelectSmilePointTradeByContrNo(String buyerNo, long contrNo) {
    var result = smilePointTradeRepository.SelectSmilePointTradeByContrNo(buyerNo, contrNo);
    if (result == null) {
      return null;
    }
    if (result.size() < 1) {
      return null;
    }
    List<SmilePointTrade> resultList = new ArrayList<SmilePointTrade>();
    SmilePointTrade smilePointTrade = new SmilePointTrade();
    for (var data : result
         ) {
      smilePointTrade.setComment(data.getComment());
      smilePointTrade.setPoint(data.getPoint());
      smilePointTrade.setApprStatus(data.getApprStatus());
      smilePointTrade.setBuyerNo(data.getCustNo());
      smilePointTrade.setContrNo(data.getContrNo());
      smilePointTrade.setErrorMsg(data.getErrorMassage());
      smilePointTrade.setExpireDate(data.getReturnExpireDate());
      smilePointTrade.setRegDate(data.getRegDt());
      smilePointTrade.setReasonCode(data.getReasonCode());
      smilePointTrade.setSmilePayNo(data.getSmilePayNo());
      smilePointTrade.setTargetType(data.getTargetType());
      smilePointTrade.setSaveType(data.getServiceType());
      smilePointTrade.setCertApprId(data.getCertApprId());
      smilePointTrade.setUserKey(data.getUserKey());
      resultList.add(smilePointTrade);
    }
    return resultList;
  }

  public List<SmilePointTrade> SelectHistory(String buyerNo, String startDate, String endData, int maxRowCount) {
    var result = smilePointTradeRepository.SelectHistory(buyerNo, startDate, endData, maxRowCount);
    if (result == null) {
      return null;
    }
    if (result.size() < 1) {
      return null;
    }
    List<SmilePointTrade> resultList = new ArrayList<SmilePointTrade>();
    SmilePointTrade smilePointTrade = new SmilePointTrade();
    for (var data : result
    ) {
      smilePointTrade.setComment(data.getComment());
      smilePointTrade.setPoint(data.getPoint());
      smilePointTrade.setApprStatus(data.getApprStatus());
      smilePointTrade.setBuyerNo(data.getCustNo());
      smilePointTrade.setContrNo(data.getContrNo());
      smilePointTrade.setErrorMsg(data.getErrorMassage());
      smilePointTrade.setExpireDate(data.getReturnExpireDate());
      smilePointTrade.setRegDate(data.getRegDt());
      smilePointTrade.setReasonCode(data.getReasonCode());
      smilePointTrade.setSmilePayNo(data.getSmilePayNo());
      smilePointTrade.setTargetType(data.getTargetType());
      smilePointTrade.setSaveType(data.getServiceType());
      smilePointTrade.setCertApprId(data.getCertApprId());
      smilePointTrade.setUserKey(data.getUserKey());
      resultList.add(smilePointTrade);
    }
    return resultList;
  }
}
