package com.zt.bookkeeping.ledger.domain.invitation.event;

import com.zt.bookkeeping.ledger.common.base.AbstractEvent;
import com.zt.bookkeeping.ledger.domain.invitation.entity.InvitationAgg;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;

public class InvitationCodeCreatedEvent extends AbstractEvent<InvitationAgg> {

    public InvitationCodeCreatedEvent(InvitationAgg data) {
        super(data, "invitationCodeCreatedEvent");
    }
}
