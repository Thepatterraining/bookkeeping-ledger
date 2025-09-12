package com.zt.bookkeeping.ledger.domain.invitation.repository;

import com.zt.bookkeeping.ledger.domain.invitation.entity.InvitationAgg;
import com.zt.bookkeeping.ledger.domain.invitation.entity.InvitationCodeVO;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;

public interface InvitationRepository {

    InvitationAgg loadLatestInvitationAgg(String ledgerNo, String inviterNo);

    Boolean isCodeExist(InvitationCodeVO code);

    void insert(InvitationAgg invitationAgg);

    InvitationAgg loadByCode(InvitationCodeVO code);

    void update(InvitationAgg invitationAgg);
}
