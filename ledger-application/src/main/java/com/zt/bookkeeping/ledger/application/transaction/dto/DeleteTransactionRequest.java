package com.zt.bookkeeping.ledger.application.transaction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DeleteTransactionRequest {

    @NotBlank
    private String ledgerNo;

}
