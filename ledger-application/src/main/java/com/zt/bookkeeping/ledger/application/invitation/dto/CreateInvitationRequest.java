package com.zt.bookkeeping.ledger.application.invitation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateInvitationRequest {

    @NotBlank
    private String ledgerNo;

}
