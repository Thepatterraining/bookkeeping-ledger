package com.zt.bookkeeping.ledger.application.ledger.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLedgerRequest {

    @NotBlank
    private String ledgerName;
}
