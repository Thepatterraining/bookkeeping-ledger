package com.zt.bookkeeping.ledger.domain.ledger.event;

import com.zt.bookkeeping.ledger.common.base.AbstractEvent;
import com.zt.bookkeeping.ledger.common.base.DomainEvent;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;

public class LedgerUpdatedEvent extends AbstractEvent<LedgerAgg> {

    public LedgerUpdatedEvent(LedgerAgg data) {
        super(data, "updatedLedger");
    }
}
