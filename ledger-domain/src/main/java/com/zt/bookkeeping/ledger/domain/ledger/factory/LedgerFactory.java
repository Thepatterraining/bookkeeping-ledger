package com.zt.bookkeeping.ledger.domain.ledger.factory;

import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerBudgetVO;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface LedgerFactory {

    LedgerAgg createLedgerAgg(String ledgerName, String userNo, String ledgerDesc, String ledgerImage);

    LedgerBudgetVO createLedgerBudget(String ledgerNo, BigDecimal budgetAmount, LocalDate date);
}
