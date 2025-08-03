package com.zt.bookkeeping.ledger.application.ledger.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class UpdateLedgerBudgetRequest {
    private String ledgerNo;
    private LocalDate budgetDate;
    private BigDecimal budgetAmount;
}
