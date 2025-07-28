package com.zt.bookkeeping.ledger.domain.ledger.factory;

import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;

public interface LedgerFactory {

    LedgerAgg createLedgerAgg(String ledgerName, String userNo, String ledgerDesc, String ledgerImage);
}
