package com.ebaykorea.payback.core.persistence.stardb.reward;

import com.ebaykorea.payback.core.common.support.GsonUtils;
import com.ebaykorea.payback.core.persistence.mssql.stardb.reward.CashbackOrderRepository;
import com.ebaykorea.payback.core.persistence.mssql.stardb.reward.entity.CashbackOrderEntity;
import com.ebaykorea.payback.core.persistence.mssql.stardb.reward.entity.CashbackOrderEntityId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = DatabaseConfigTest.class)
public class CashbackOrderRepositoryTest {

    @Autowired
    CashbackOrderRepository cashbackOrderRepository;

    @Test
    public void findByBuyOrderNo() {
        CashbackOrderEntity cashbackOrder = cashbackOrderRepository.findByBuyOrderNo(4681185340L);
        System.out.println(GsonUtils.toJsonPretty(cashbackOrder));
        assertNotNull(cashbackOrder);
    }

    @Test
    public void findByEntityId() {
        Optional<CashbackOrderEntity> cashbackOrder = cashbackOrderRepository.findById(
                CashbackOrderEntityId
                        .builder()
                        .buyOrderNo(4681185340L)
                        .cashbackType("I")
                        .tradeCd("SVC")
                        .build()
        );
        System.out.println(GsonUtils.toJsonPretty(cashbackOrder.get()));
        assertNotNull(cashbackOrder);
    }

    @Test
    public void update() {
        Optional<CashbackOrderEntity> cashbackOrder = Optional.ofNullable(cashbackOrderRepository.findByBuyOrderNo(4681185340L));

        cashbackOrder.ifPresent(updateCashbackOrder ->{
            updateCashbackOrder.setCashbackMoney(BigDecimal.valueOf(60.00));
            updateCashbackOrder.setCashbackBasisMoney(BigDecimal.valueOf(3000.00));
            cashbackOrderRepository.save(updateCashbackOrder);
        });

        System.out.println(GsonUtils.toJsonPretty(cashbackOrder));
    }

    @Test
    public void save() {
        CashbackOrderEntity cashbackOrder = new CashbackOrderEntity();
        cashbackOrder.setBuyOrderNo(4681185340L);
        cashbackOrder.setCashbackType("I");
        cashbackOrder.setTradeCd("SVC");
        cashbackOrder.setCashbackMoney(BigDecimal.valueOf(100));
        cashbackOrder.setCashbackBasisMoney(BigDecimal.valueOf(5000));
        cashbackOrder.setGdNo("828713049");
        cashbackOrder.setPackNo(4051530904L);
        cashbackOrder.setCustNo("103574394");
        cashbackOrder.setUserKey("TEST-USERKEY");
        cashbackOrder.setTradeStatus("90");
        cashbackOrder.setUseEnableDt(Instant.now());
        cashbackOrder.setSiteType("G");
        cashbackOrder.setIsRequestCancel(Boolean.FALSE);
        cashbackOrder.setRegId("TESTER");
        cashbackOrderRepository.save(cashbackOrder);
    }

    @Test
    public void delete() {
        Optional<CashbackOrderEntity> cashbackOrder = cashbackOrderRepository.findById(
                CashbackOrderEntityId
                        .builder()
                        .buyOrderNo(4681185340L)
                        .cashbackType("I")
                        .tradeCd("SVC")
                        .build()
        );
        cashbackOrderRepository.delete(cashbackOrder.get());

    }
}