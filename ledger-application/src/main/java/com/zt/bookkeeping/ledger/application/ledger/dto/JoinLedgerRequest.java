package com.zt.bookkeeping.ledger.application.ledger.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class JoinLedgerRequest {
    @NotBlank
    private String invitationCode;
}
