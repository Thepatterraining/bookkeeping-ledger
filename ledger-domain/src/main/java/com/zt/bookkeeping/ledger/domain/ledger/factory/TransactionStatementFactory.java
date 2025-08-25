package com.zt.bookkeeping.ledger.domain.ledger.factory;

import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerBudgetVO;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.CategoryVO;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.TransactionStatementAgg;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TransactionStatementFactory {

    TransactionStatementAgg createTransactionStatementAgg(String ledgerNo, BigDecimal amount,
            Integer transactionType, String transactionDesc, String categoryNo, Integer categoryType, String userNo);

}
