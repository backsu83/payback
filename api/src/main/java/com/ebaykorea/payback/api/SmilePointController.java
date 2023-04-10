package com.ebaykorea.payback.api;

import com.ebaykorea.payback.core.dto.*;
import com.ebaykorea.payback.infrastructure.persistence.repository.SmilePointRepository;
import com.ebaykorea.payback.infrastructure.query.SmilePointTradeQuery;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "SmilePoint", description = "스마일포인트 관련 Api")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/SmilePoint")
public class SmilePointController {

  private final SmilePointRepository smilePointRepository;
  private final SmilePointTradeQuery smilePointTradeQuery;

  /**
   * 스마일 포인트 적립 요청
   *
   * @param request
   * @return
   */
  @PostMapping("/SaveRequest")
  public SmilePointResponseDto saveSmilePointRequest(final @Valid @RequestBody SaveSmilePointRequestDto request) {

    if (request == null) {
      return new SmilePointResponseDto("G100","request 누락", null) ;
    }

    if (request.getBuyerNo() == null || request.getBuyerNo() == "") {
      return new SmilePointResponseDto("G100", "BuyerId 누락", -1L);
    }
    if (request.getPointAmount() <= 0) {
      return new SmilePointResponseDto("G100", "Point Amount 누락", -1L);
    }
    if (request.getReasonCode() <= 0) {
      return new SmilePointResponseDto("G100", "ReasonCode 누락", -1L);
    }

    long smilePayNo = 0L;
    try{
      smilePayNo = smilePointRepository.setSmilePoint(
              request.getBuyerNo(),
              request.getPointAmount(),
              request.getReasonCode(),
              request.getContrNo(),
              request.getComment(),
              request.getEasNo(),
              request.getEventId(),
              0,
              20,
              request.getWinNo(),
              request.getSellerId()
      );
    }
    catch(Exception e) {

    }

    if (smilePayNo == -6L) {
      return new SmilePointResponseDto("G401", "유저 키 정보 없음", -1L);
    }
    if (smilePayNo == -1L) {
      return new SmilePointResponseDto("G500", "서비스 내부 문제로 처리 에러 발생", -1L);
    }
    if (smilePayNo == -99L) {
      return new SmilePointResponseDto("G101", "ReasonCode 존재하지 않음", -1L);
    }

    return new SmilePointResponseDto("0000", "", smilePayNo);
  }

  /**
   * 스마일 포인트 적립 요청
   *
   * @param request
   * @return
   */
  @PostMapping("/StatusBySmilePayNo")
  public SmilePointResponseDto statusBySmilePayNo(final @Valid @RequestBody SmilePointStatusSmilepayRequestDto request) {
    if(request.getSmilePayNo() <= 0) {
      return new SmilePointResponseDto("G100","SmilePayNo 누락", null) ;
    }
    var result = smilePointTradeQuery.getSmilePointTradeBySmilePayNo(request.getSmilePayNo());
    return new SmilePointResponseDto("0000","", result) ;
  }

  /**
   * 스마일 포인트 적립 smilePayNo
   *
   * @param request
   * @return
   */
  @PostMapping("/StatusByContrNo")
  public SmilePointResponseDto statusByContrNo(final @Valid @RequestBody SmilePointStatusContrNoRequestDto request) {
    if (request == null) {
      return new SmilePointResponseDto("G100","request 누락", null);
    }
    if(request.getBuyerNo() == null  || "".equals(request.getBuyerNo())) {
      return new SmilePointResponseDto("G100","BuyerNo 누락", null);
    }
    if (request.getContrNo() <= 0) {
      return new SmilePointResponseDto("G101","ContrNo 는 0 이상 입력", null);
    }
    var result = smilePointTradeQuery.findSmilePointTradesByContrNo(request.getBuyerNo(), request.getContrNo());
    return new SmilePointResponseDto("0000","", result) ;
  }

  /**
   * 스마일 포인트 적립 요청
   *
   * @param request
   * @return
   */
  @PostMapping("/History")
  public SmilePointResponseDto history(final @Valid @RequestBody SmilePointHistoryRequestDto request) {
    if (request == null) {
      return new SmilePointResponseDto("G100","request 누락", null);
    }
    if(request.getBuyerNo() == null  || "".equals(request.getBuyerNo())) {
      return new SmilePointResponseDto("G100","BuyerNo 누락", null);
    }
    if(request.getStartDate() == null  || "".equals(request.getStartDate())) {
      return new SmilePointResponseDto("G100","Start Date 누락", null);
    }
    if(request.getEndDate() == null  || "".equals(request.getEndDate())) {
      return new SmilePointResponseDto("G100","End Date 누락", null);
    }
    if (request.getMaxRowCount() <= 0) {
      request.setMaxRowCount(1000);
    }

    var result = smilePointTradeQuery.findHistories(request.getBuyerNo(), request.getStartDate(), request.getEndDate(), request.getMaxRowCount());
    return new SmilePointResponseDto("0000","", result) ;
  }
}

