package com.zt.bookkeeping.ledger.domain.ledger.event;

import com.zt.bookkeeping.ledger.common.base.AbstractEvent;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;

public class UserJoinedLedgerEvent extends AbstractEvent<LedgerAgg> {

    public UserJoinedLedgerEvent(LedgerAgg data) {
        super(data, "用户加入账本");
    }
}
