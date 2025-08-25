package com.zt.bookkeeping.ledger.application.ledger.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateTransactionRequest {

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Integer transactionType;

    @NotBlank
    private String transactionDesc;

    @NotBlank
    private String categoryNo;

    @NotNull
    private Integer categoryType;

    @NotBlank
    private String ledgerNo;

}
