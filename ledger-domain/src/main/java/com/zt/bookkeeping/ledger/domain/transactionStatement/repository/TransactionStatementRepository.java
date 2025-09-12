package com.zt.bookkeeping.ledger.domain.transactionStatement.repository;

import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.CategoryVO;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.TransactionStatementAgg;

public interface TransactionStatementRepository {

    void insert(TransactionStatementAgg transactionStatementAgg);

    void insert(CategoryVO categoryVO);

    void update(TransactionStatementAgg transactionStatementAgg);

    TransactionStatementAgg load(String transactionStatementNo);
}
