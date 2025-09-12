package com.zt.bookkeeping.ledger.infrastructure.factory;

import com.zt.bookkeeping.ledger.domain.generator.SnowFlakeGenerator;
import com.zt.bookkeeping.ledger.domain.ledger.entity.*;
import com.zt.bookkeeping.ledger.domain.ledger.factory.LedgerFactory;
import com.zt.bookkeeping.ledger.infrastructure.util.MoneyUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
public class LedgerFactoryImpl implements LedgerFactory {

    @Resource
    private SnowFlakeGenerator snowFlakeGenerator;

    @Override
    public LedgerAgg createLedgerAgg(String ledgerName, String userNo, String ledgerDesc, String ledgerImage) {
        // 生成ledger no
        String ledgerNo = snowFlakeGenerator.nextId("ledger");
        LedgerMemberEntity memberEntity = createLedgerMember(ledgerNo, userNo, LedgerMemberRoleVO.ADMIN);
        return LedgerAgg.builder()
                .ledgerName(ledgerName)
                .ledgerDesc(ledgerDesc)
                .ownerNo(userNo)
                .ledgerImage(ledgerImage)
                .ledgerNo(ledgerNo)
                .lastLedgerBudget(createLedgerBudget(ledgerNo, BigDecimal.ZERO, LocalDate.now()))
                .memberSet(Set.of(memberEntity))
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

    @Override
    public LedgerMemberEntity createLedgerMember(String ledgerNo, String userNo, LedgerMemberRoleVO role) {
        return LedgerMemberEntity.builder()
                .ledgerNo(ledgerNo)
                .userNo(userNo)
                .role(role)
                .status(LedgerMemberStatusVO.NORMAL)
                .joinTime(LocalDateTime.now())
                .build();
    }

    @Override
    public LedgerSummaryVO createLedgerSummary(Long income, Long expense) {
        BigDecimal incomeAmount = MoneyUtil.fen2Yuan(income);
        BigDecimal expenseAmount = MoneyUtil.fen2Yuan(expense);
        return LedgerSummaryVO.builder()
                .income(incomeAmount)
                .expense(expenseAmount)
                .remained(incomeAmount.subtract(expenseAmount))
                .date(LocalDate.now())
                .build();
    }
}
