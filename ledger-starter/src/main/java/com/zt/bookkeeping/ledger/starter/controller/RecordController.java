package com.zt.bookkeeping.ledger.starter.controller;

import com.zt.bookkeeping.ledger.application.ledger.dto.CreateLedgerRequest;
import com.zt.bookkeeping.ledger.application.ledger.dto.LedgerListRes;
import com.zt.bookkeeping.ledger.application.ledger.dto.QueryLedgerListRequest;
import com.zt.bookkeeping.ledger.application.ledger.dto.UpdateLedgerRequest;
import com.zt.bookkeeping.ledger.application.ledger.service.LedgerCommandApplicationService;
import com.zt.bookkeeping.ledger.application.ledger.service.LedgerQueryApplicationService;
import com.zt.bookkeeping.ledger.common.res.PageRes;
import com.zt.bookkeeping.ledger.common.res.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Resource
    private LedgerCommandApplicationService ledgerCommandApplicationService;

    @Resource
    private LedgerQueryApplicationService ledgerQueryApplicationService;

    @PostMapping("/create")
    public Result<String> createLedger(CreateLedgerRequest request) {
        String ledgerNo = ledgerCommandApplicationService.createLedger(request);
        return Result.success(ledgerNo);
    }

    @PostMapping("/update")
    public Result<String> updateLedger(UpdateLedgerRequest request) {
        ledgerCommandApplicationService.updateLedger(request);
        return Result.success();
    }

    @PostMapping("/list")
    public Result<PageRes<LedgerListRes>> deleteLedger(QueryLedgerListRequest request) {
        return Result.success(ledgerQueryApplicationService.getLedgerList(request));
    }
}
