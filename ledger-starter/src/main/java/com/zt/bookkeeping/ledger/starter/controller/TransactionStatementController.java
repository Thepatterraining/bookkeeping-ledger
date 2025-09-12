package com.zt.bookkeeping.ledger.starter.controller;

import com.zt.bookkeeping.ledger.application.transaction.dto.CreateTransactionRequest;
import com.zt.bookkeeping.ledger.application.transaction.dto.DeleteTransactionRequest;
import com.zt.bookkeeping.ledger.application.transaction.dto.QueryTransactionListRequest;
import com.zt.bookkeeping.ledger.application.transaction.dto.TransactionListRes;
import com.zt.bookkeeping.ledger.application.transaction.service.TransactionStatementCommandApplicationService;
import com.zt.bookkeeping.ledger.application.transaction.service.TransactionStatementQueryApplicationService;
import com.zt.bookkeeping.ledger.common.res.PageRes;
import com.zt.bookkeeping.ledger.common.res.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactionStatement")
public class TransactionStatementController {

    @Resource
    private TransactionStatementCommandApplicationService transactionStatementCommandApplicationService;

    @Resource
    private TransactionStatementQueryApplicationService transactionStatementQueryApplicationService;

    @PostMapping("/create")
    public Result<String> createTransaction(@RequestBody CreateTransactionRequest request) {
        String ledgerNo = transactionStatementCommandApplicationService.createTransactionStatement(request);
        return Result.success(ledgerNo);
    }

    @GetMapping("/list")
    public Result<PageRes<TransactionListRes>> getTransactionList(QueryTransactionListRequest request) {
        return Result.success(transactionStatementQueryApplicationService.getTransactionList(request));
    }

    @DeleteMapping("/delete/{no}")
    public Result<String> deleteTransaction(@PathVariable String no, @RequestBody DeleteTransactionRequest request) {
        transactionStatementCommandApplicationService.deleteTransaction(no, request);
        return Result.success();
    }
}
