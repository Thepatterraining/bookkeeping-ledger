package com.zt.bookkeeping.ledger.application.ledger.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LedgerDetailRes {
    private String ledgerName;
    private String ledgerNo;
    private String ledgerStatus;
    private String ledgerDesc;
    private String ledgerImage;
    private LedgerSummaryRes ledgerSummary;
    private LedgerBudgetRes ledgerBudget;
}
