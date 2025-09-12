package com.zt.bookkeeping.ledger.domain.invitation.factory;

import com.zt.bookkeeping.ledger.domain.invitation.entity.InvitationAgg;
import com.zt.bookkeeping.ledger.domain.invitation.entity.InvitationCodeVO;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerBudgetVO;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface InvitationFactory {

    InvitationAgg createInvitationAgg(String ledgerNo, String userNo, InvitationCodeVO invitationCodeVO);
}
