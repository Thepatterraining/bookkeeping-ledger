package com.zt.bookkeeping.ledger.domain.ledger.event;

import com.zt.bookkeeping.ledger.common.base.AbstractEvent;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;

public class LedgerDeletedEvent extends AbstractEvent<LedgerAgg> {

    public LedgerDeletedEvent(LedgerAgg data) {
        super(data, "ledger_deleted");
    }
}
