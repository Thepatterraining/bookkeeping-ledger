package com.zt.bookkeeping.ledger.infrastructure.factory;

import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerStatusVO;
import com.zt.bookkeeping.ledger.domain.ledger.factory.LedgerFactory;

import java.util.UUID;

public class LedgerFactoryImpl implements LedgerFactory {

    @Override
    public LedgerAgg createLedgerAgg(String ledgerName, String userNo, String ledgerDesc, String ledgerImage) {
        // 生成ledger no todo
        String ledgerNo = UUID.randomUUID().toString();
        return LedgerAgg.builder()
                .ledgerName(ledgerName)
                .ledgerDesc(ledgerDesc)
                .ownerNo(userNo)
                .ledgerImage(ledgerImage)
                .ledgerNo(ledgerNo)
                .build();
    }
}
