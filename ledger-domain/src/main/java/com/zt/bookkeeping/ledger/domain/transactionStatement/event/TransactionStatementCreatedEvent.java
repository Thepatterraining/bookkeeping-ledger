package com.zt.bookkeeping.ledger.domain.transactionStatement.event;

import com.zt.bookkeeping.ledger.common.base.AbstractEvent;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.TransactionStatementAgg;

public class TransactionStatementCreatedEvent extends AbstractEvent<TransactionStatementAgg> {

    public TransactionStatementCreatedEvent(TransactionStatementAgg data) {
        super(data, "transactionStatementCreatedEvent");
    }
}
