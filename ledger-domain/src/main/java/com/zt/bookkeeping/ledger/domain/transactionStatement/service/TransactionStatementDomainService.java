package com.zt.bookkeeping.ledger.domain.transactionStatement.service;

import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import com.zt.bookkeeping.ledger.domain.ledger.repository.LedgerRepository;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.CategoryVO;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.TransactionStatementAgg;
import com.zt.bookkeeping.ledger.domain.transactionStatement.repository.TransactionStatementRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TransactionStatementDomainService {

    @Resource
    private TransactionStatementRepository transactionStatementRepository;

    public void save(TransactionStatementAgg transactionStatementAgg) {
        if (transactionStatementAgg.getId() == null) {
            transactionStatementRepository.insert(transactionStatementAgg);
        } else {
            transactionStatementRepository.update(transactionStatementAgg);
        }
    }

    public TransactionStatementAgg findByNo(String transactionStatementNo) {
        return transactionStatementRepository.load(transactionStatementNo);
    }

    public void delete(TransactionStatementAgg transactionStatementAgg) {
        transactionStatementRepository.update(transactionStatementAgg);
    }
}
