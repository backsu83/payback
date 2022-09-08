package com.ebaykorea.payback.api.controller.query;

import com.ebaykorea.payback.api.common.annotation.LogExcutor;
import com.ebaykorea.payback.api.controller.dto.CashbackOrderRequest;
import com.ebaykorea.payback.api.controller.dto.CashbackOrderResponse;
import com.ebaykorea.payback.api.controller.dto.CashbackRequest;
import com.ebaykorea.payback.api.controller.dto.CashbackResponse;
import com.ebaykorea.payback.api.controller.mapper.QueryRequestMapper;
import com.ebaykorea.payback.api.controller.mapper.QueryResponseMapper;
import com.ebaykorea.payback.core.domain.query.CashbackQuery;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "리워드 조회", description = "리워드 조회 테스트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Read")
public class CashbackQueryController {

    private final CashbackQuery rewardQuery;
    private final QueryResponseMapper responseMapper;
    private final QueryRequestMapper requestMapper;

    /**
     * api 조회 테스트
     * @param request
     * @return
     */
    @LogExcutor
    @PostMapping("/V2/CashbackReward")
    public ResponseEntity<CashbackResponse> getCashbackReward(final @Validated @RequestBody CashbackRequest request) {
        return ResponseEntity.ok(responseMapper.as(rewardQuery.getCashbackReward(requestMapper.as(request))));
    }

    /**
     * db + api 조회 테스트
     * @param request
     * @return
     */
    @LogExcutor
    @PostMapping("/V2/CashbackReward/order")
    public ResponseEntity<CashbackOrderResponse> getCashbackRewardOrder(final @Validated @RequestBody CashbackOrderRequest request) {
        return ResponseEntity.ok(responseMapper.as(rewardQuery.getCashbackOrder(
                request.getBuyOrderNo(),
                requestMapper.as(request.getReward())
        )));
    }
}

