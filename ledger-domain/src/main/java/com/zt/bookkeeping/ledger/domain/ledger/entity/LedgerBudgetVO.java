package com.zt.bookkeeping.ledger.domain.ledger.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class LedgerBudgetVO {
    private Long id;
    private String ledgerNo;
    private Integer budgetAmount;
    private Integer usedAmount;
    private Integer remainedAmount;
    private LocalDate budgetDate;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
