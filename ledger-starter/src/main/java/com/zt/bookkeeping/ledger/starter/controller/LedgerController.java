package com.zt.bookkeeping.ledger.starter.controller;

import com.zt.bookkeeping.ledger.application.ledger.dto.*;
import com.zt.bookkeeping.ledger.application.ledger.service.LedgerCommandApplicationService;
import com.zt.bookkeeping.ledger.application.ledger.service.LedgerQueryApplicationService;
import com.zt.bookkeeping.ledger.common.res.PageRes;
import com.zt.bookkeeping.ledger.common.res.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Result<String> updateLedger(@RequestBody UpdateLedgerRequest request) {
        ledgerCommandApplicationService.updateLedger(request);
        return Result.success();
    }

    @DeleteMapping()
    public Result<String> deleteLedger(@RequestBody DeleteLedgerRequest request) {
        ledgerCommandApplicationService.deleteLedger(request.getLedgerNo());
        return Result.success();
    }

    @GetMapping("/list")
    public Result<PageRes<LedgerListRes>> getLedgerList(QueryLedgerListRequest request) {
        return Result.success(ledgerQueryApplicationService.getLedgerList(request));
    }

    @GetMapping("/detail")
    public Result<LedgerDetailRes> getLedgerDetail(QueryLedgerRequest request) {
        return Result.success(ledgerQueryApplicationService.getLedger(request));
    }

    @PostMapping("/update/budget")
    public Result<String> updateLedgerBudget(@RequestBody UpdateLedgerBudgetRequest request) {
        ledgerCommandApplicationService.updateLedgerBudget(request);
        return Result.success();
    }

    @PostMapping("/join")
    public Result<String> joinLedger(@RequestBody JoinLedgerRequest request) {
        ledgerCommandApplicationService.joinLedger(request);
        return Result.success();
    }

    @GetMapping("/memberList")
    public Result<List<LedgerMemberListRes>> memberList(QueryLedgerMemberListRequest request) {
        List<LedgerMemberListRes> memberList = ledgerQueryApplicationService.getMemberList(request);
        return Result.success(memberList);
    }
}
