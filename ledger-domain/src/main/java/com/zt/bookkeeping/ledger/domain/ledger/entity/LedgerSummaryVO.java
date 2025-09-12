package com.zt.bookkeeping.ledger.domain.ledger.entity;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class LedgerSummaryVO {
    private BigDecimal income;
    private BigDecimal expense;
    private BigDecimal remained;
    private LocalDate date;

}
