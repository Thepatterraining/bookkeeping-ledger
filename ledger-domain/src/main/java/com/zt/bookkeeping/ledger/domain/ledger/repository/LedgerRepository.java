package com.zt.bookkeeping.ledger.domain.ledger.repository;

import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;

public interface LedgerRepository {

    void insert(LedgerAgg userAgg);

    LedgerAgg findByNameInUser(String name, String userNo);

    LedgerAgg findByNoInUser(String ledgerNo, String userNo);

    void update(LedgerAgg ledgerAgg);
}
