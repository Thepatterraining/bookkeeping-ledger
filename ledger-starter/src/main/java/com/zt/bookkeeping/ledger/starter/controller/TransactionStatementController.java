package com.zt.bookkeeping.ledger.starter.controller;

import com.zt.bookkeeping.ledger.application.ledger.dto.*;
import com.zt.bookkeeping.ledger.application.ledger.service.LedgerCommandApplicationService;
import com.zt.bookkeeping.ledger.application.ledger.service.LedgerQueryApplicationService;
import com.zt.bookkeeping.ledger.application.ledger.service.TransactionStatementCommandApplicationService;
import com.zt.bookkeeping.ledger.application.ledger.service.TransactionStatementQueryApplicationService;
import com.zt.bookkeeping.ledger.common.res.PageRes;
import com.zt.bookkeeping.ledger.common.res.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactionStatement")
public class TransactionStatementController {

    @Resource
    private TransactionStatementCommandApplicationService transactionStatementCommandApplicationService;

    @Resource
    private TransactionStatementQueryApplicationService transactionStatementQueryApplicationService;

    @PostMapping("/create")
    public Result<String> createTransaction(CreateTransactionRequest request) {
        String ledgerNo = transactionStatementCommandApplicationService.createTransactionStatement(request);
        return Result.success(ledgerNo);
    }

    @PostMapping("/list")
    public Result<PageRes<TransactionListRes>> deleteLedger(QueryTransactionListRequest request) {
        return Result.success(transactionStatementQueryApplicationService.getTransactionList(request));
    }
}
