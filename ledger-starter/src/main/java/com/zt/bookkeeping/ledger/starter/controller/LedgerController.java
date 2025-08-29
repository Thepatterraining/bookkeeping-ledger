package com.zt.bookkeeping.ledger.starter.controller;

import com.zt.bookkeeping.ledger.application.ledger.dto.*;
import com.zt.bookkeeping.ledger.application.ledger.service.LedgerCommandApplicationService;
import com.zt.bookkeeping.ledger.application.ledger.service.LedgerQueryApplicationService;
import com.zt.bookkeeping.ledger.common.res.PageRes;
import com.zt.bookkeeping.ledger.common.res.Result;
import com.zt.bookkeeping.ledger.domain.ledger.repository.LedgerRepository;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ledger")
public class LedgerController {

    @Resource
    private LedgerCommandApplicationService ledgerCommandApplicationService;

    @Resource
    private LedgerQueryApplicationService ledgerQueryApplicationService;

    @PostMapping("/create")
    public Result<String> createLedger(@RequestBody CreateLedgerRequest request) {
        String ledgerNo = ledgerCommandApplicationService.createLedger(request);
        return Result.success(ledgerNo);
    }

    @PostMapping("/update")
    public Result<String> updateLedger(UpdateLedgerRequest request) {
        ledgerCommandApplicationService.updateLedger(request);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<PageRes<LedgerListRes>> deleteLedger(QueryLedgerListRequest request) {
        return Result.success(ledgerQueryApplicationService.getLedgerList(request));
    }

    @PostMapping("/update/budget")
    public Result<String> updateLedgerBudget(UpdateLedgerBudgetRequest request) {
        ledgerCommandApplicationService.updateLedgerBudget(request);
        return Result.success();
    }
}
