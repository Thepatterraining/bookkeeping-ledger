package com.zt.bookkeeping.ledger.starter.controller;

import com.zt.bookkeeping.ledger.application.invitation.dto.CreateInvitationRequest;
import com.zt.bookkeeping.ledger.application.invitation.dto.UseInvitationCodeRequest;
import com.zt.bookkeeping.ledger.application.invitation.service.InvitationCommandApplicationService;
import com.zt.bookkeeping.ledger.application.ledger.dto.*;
import com.zt.bookkeeping.ledger.application.ledger.service.LedgerCommandApplicationService;
import com.zt.bookkeeping.ledger.application.ledger.service.LedgerQueryApplicationService;
import com.zt.bookkeeping.ledger.common.res.PageRes;
import com.zt.bookkeeping.ledger.common.res.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invitation")
public class InvitationController {

    @Resource
    private InvitationCommandApplicationService invitationCommandApplicationService;

    @GetMapping("/getLatestCode")
    public Result<String> getLatestCode(CreateInvitationRequest request) {
        String ledgerNo = invitationCommandApplicationService.getLatestCode(request);
        return Result.success(ledgerNo);
    }

    @PostMapping("/useCode")
    public Result<String> useCode(@RequestBody UseInvitationCodeRequest request) {
        invitationCommandApplicationService.useCode(request);
        return Result.success();
    }
}
