package com.zt.bookkeeping.ledger.infrastructure.factory;

import com.zt.bookkeeping.ledger.domain.generator.SnowFlakeGenerator;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerBudgetVO;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerStatusVO;
import com.zt.bookkeeping.ledger.domain.ledger.factory.LedgerFactory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Component
public class LedgerFactoryImpl implements LedgerFactory {

    @Resource
    private SnowFlakeGenerator snowFlakeGenerator;

    @Override
    public LedgerAgg createLedgerAgg(String ledgerName, String userNo, String ledgerDesc, String ledgerImage) {
        // 生成ledger no
        String ledgerNo = snowFlakeGenerator.nextId("ledger");
        return LedgerAgg.builder()
                .ledgerName(ledgerName)
                .ledgerDesc(ledgerDesc)
                .ownerNo(userNo)
                .ledgerImage(ledgerImage)
                .ledgerNo(ledgerNo)
                .lastLedgerBudget(createLedgerBudget(ledgerNo, BigDecimal.ZERO, LocalDate.now()))
                .build();
    }

    @Override
    public LedgerBudgetVO createLedgerBudget(String ledgerNo, BigDecimal budgetAmount, LocalDate date) {
        // 生成ledger budget
        Integer amount = budgetAmount.multiply(new BigDecimal(100)).intValue();
        return LedgerBudgetVO.builder()
                .budgetAmount(amount)
                .budgetDate(date)
                .usedAmount(0)
                .remainedAmount(amount)
                .ledgerNo(ledgerNo)
                .build();
    }
}
