package com.zt.bookkeeping.ledger.application.invitation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UseInvitationCodeRequest {

    @NotBlank
    private String InvitationCode;

}
