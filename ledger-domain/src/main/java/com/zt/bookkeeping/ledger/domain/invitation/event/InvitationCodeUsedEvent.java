package com.zt.bookkeeping.ledger.domain.invitation.event;

import com.zt.bookkeeping.ledger.common.base.AbstractEvent;
import com.zt.bookkeeping.ledger.domain.invitation.entity.InvitationAgg;

public class InvitationCodeUsedEvent extends AbstractEvent<InvitationAgg> {

    public InvitationCodeUsedEvent(InvitationAgg data) {
        super(data, "invitationCodeUsed");
    }
}
