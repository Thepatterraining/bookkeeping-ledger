package com.zt.bookkeeping.ledger.application.ledger.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LedgerBudgetRes {
    private String amount;
    private String used;
    private String remained;
    private String budgetDate;
}
