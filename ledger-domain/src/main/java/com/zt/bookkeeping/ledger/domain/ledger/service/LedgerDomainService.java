package com.zt.bookkeeping.ledger.domain.ledger.service;

import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import com.zt.bookkeeping.ledger.domain.ledger.repository.LedgerRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class LedgerDomainService {

    @Resource
    private LedgerRepository ledgerRepository;

    public LedgerAgg findByNameInUser(String name, String userNo) {
        return ledgerRepository.findByNameInUser(name, userNo);
    }

    public LedgerAgg findByNo(String ledgerNo) {
        return ledgerRepository.load(ledgerNo);
    }

    public void save(LedgerAgg userAgg) {
        if (userAgg.getId() == null) {
            ledgerRepository.insert(userAgg);
        } else {
            ledgerRepository.update(userAgg);
        }
    }
}
