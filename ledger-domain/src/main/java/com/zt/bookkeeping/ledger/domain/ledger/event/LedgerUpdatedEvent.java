package com.zt.bookkeeping.ledger.domain.ledger.event;

import com.zt.bookkeeping.ledger.common.base.DomainEvent;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;

public class LedgerUpdatedEvent implements DomainEvent {
    private LedgerAgg data;

    public LedgerUpdatedEvent(LedgerAgg data) {
        this.data = data;
    }
}
