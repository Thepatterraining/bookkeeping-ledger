package com.zt.bookkeeping.ledger.domain.ledger.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LedgerAgg {
    private Long id;
    private String ledgerNo;
    private String ledgerName;
    private String ownerNo;
    private Integer ledgerStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 最新的一个预算信息
    private LedgerBudgetVO lastLedgerBudget;
}
