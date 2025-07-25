package com.zt.bookkeeping.ledger.application.ledger.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateLedgerRequest {
    private String ledgerNo;
    private String ledgerName;
    private String ledgerDesc;
    private String ledgerImage;
}
