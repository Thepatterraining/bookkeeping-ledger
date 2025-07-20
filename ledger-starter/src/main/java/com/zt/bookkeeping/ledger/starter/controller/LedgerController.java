package com.zt.bookkeeping.ledger.starter.controller;

import com.zt.bookkeeping.ledger.application.ledger.dto.CreateLedgerRequest;
import com.zt.bookkeeping.ledger.application.ledger.service.LedgerCommandApplicationService;
import com.zt.bookkeeping.ledger.common.res.Result;
import com.zt.bookkeeping.ledger.domain.ledger.repository.LedgerRepository;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ledger")
public class LedgerController {

    @Resource
    private LedgerCommandApplicationService ledgerCommandApplicationService;

    public Result<String> createLedger(CreateLedgerRequest request) {
        String ledgerNo = ledgerCommandApplicationService.createLedger(request);
        return Result.success(ledgerNo);
    }
}
