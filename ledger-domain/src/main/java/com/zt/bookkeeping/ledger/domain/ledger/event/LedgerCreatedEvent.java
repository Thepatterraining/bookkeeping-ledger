package com.zt.bookkeeping.ledger.domain.ledger.event;

import com.zt.bookkeeping.ledger.common.base.AbstractEvent;
import com.zt.bookkeeping.ledger.common.base.DomainEvent;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;

public class LedgerCreatedEvent extends AbstractEvent<LedgerAgg> {

    public LedgerCreatedEvent(LedgerAgg data) {
        super(data, "createdLedger");
    }
}
