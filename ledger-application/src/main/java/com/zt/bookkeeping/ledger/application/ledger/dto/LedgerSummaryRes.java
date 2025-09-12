package com.zt.bookkeeping.ledger.application.ledger.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LedgerSummaryRes {
    private BigDecimal income;
    private BigDecimal expense;
    private BigDecimal remained;
}
