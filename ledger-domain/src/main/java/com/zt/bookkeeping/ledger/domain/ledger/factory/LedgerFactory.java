package com.zt.bookkeeping.ledger.domain.ledger.factory;

import com.zt.bookkeeping.ledger.domain.ledger.entity.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface LedgerFactory {

    LedgerAgg createLedgerAgg(String ledgerName, String userNo, String ledgerDesc, String ledgerImage);

    LedgerBudgetVO createLedgerBudget(String ledgerNo, BigDecimal budgetAmount, LocalDate date);

    LedgerMemberEntity createLedgerMember(String ledgerNo, String userNo, LedgerMemberRoleVO role);

    LedgerSummaryVO createLedgerSummary(Long income, Long expense);
}
