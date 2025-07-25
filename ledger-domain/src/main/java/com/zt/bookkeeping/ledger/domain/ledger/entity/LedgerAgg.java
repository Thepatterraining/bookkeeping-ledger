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
    private String ledgerDesc;
    private String ledgerImage;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 最新的一个预算信息
    private LedgerBudgetVO lastLedgerBudget;

    public void save(LedgerAgg ledgerAgg) {
        updateSelf(ledgerAgg);
    }

    private void updateSelf(LedgerAgg ledgerAgg) {
        this.ledgerName = ledgerAgg.getLedgerName();
        this.ledgerDesc = ledgerAgg.getLedgerDesc();
        this.ledgerImage = ledgerAgg.getLedgerImage();
        this.ledgerStatus = ledgerAgg.getLedgerStatus();
    }
}
