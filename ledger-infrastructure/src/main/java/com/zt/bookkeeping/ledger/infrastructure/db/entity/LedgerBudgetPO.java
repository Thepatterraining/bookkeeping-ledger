package com.zt.bookkeeping.ledger.infrastructure.db.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class LedgerBudgetPO {
    private Long id;
    private String ledgerNo;
    private Integer budgetAmount;
    private Integer usedAmount;
    private Integer remainedAmount;
    private LocalDate budgetDate;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
