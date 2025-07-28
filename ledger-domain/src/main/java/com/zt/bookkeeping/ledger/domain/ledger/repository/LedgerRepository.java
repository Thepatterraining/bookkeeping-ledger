package com.zt.bookkeeping.ledger.domain.ledger.repository;

import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;

public interface LedgerRepository {

    void insert(LedgerAgg userAgg);

    LedgerAgg findByNameInUser(String name, String userNo);

    LedgerAgg load(String ledgerNo);

    void update(LedgerAgg ledgerAgg);
}
