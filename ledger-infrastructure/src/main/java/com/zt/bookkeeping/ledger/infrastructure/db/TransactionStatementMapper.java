package com.zt.bookkeeping.ledger.infrastructure.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.LedgerPO;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.TransactionStatementPO;

public interface TransactionStatementMapper extends BaseMapper<TransactionStatementPO> {

    public Long getSummaryAmount(String ledgerNo, int type);
}
