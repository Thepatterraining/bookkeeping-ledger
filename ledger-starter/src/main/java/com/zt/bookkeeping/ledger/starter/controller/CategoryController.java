package com.zt.bookkeeping.ledger.starter.controller;

import com.zt.bookkeeping.ledger.application.ledger.dto.*;
import com.zt.bookkeeping.ledger.application.ledger.service.CategoryCommandApplicationService;
import com.zt.bookkeeping.ledger.application.ledger.service.CategoryQueryApplicationService;
import com.zt.bookkeeping.ledger.application.ledger.service.LedgerQueryApplicationService;
import com.zt.bookkeeping.ledger.application.ledger.service.TransactionStatementCommandApplicationService;
import com.zt.bookkeeping.ledger.common.res.PageRes;
import com.zt.bookkeeping.ledger.common.res.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryCommandApplicationService categoryCommandApplicationService;

    @Resource
    private CategoryQueryApplicationService categoryQueryApplicationService;

    @PostMapping("/user/create")
    public Result<String> createUserCategory(@RequestBody CreateUserCategoryRequest request) {
        String ledgerNo = categoryCommandApplicationService.createCategory(request);
        return Result.success(ledgerNo);
    }

    @GetMapping("/all/list")
    public Result<PageRes<CategoryListRes>> getUserAllCategoryList(QueryLedgerListRequest request) {
        return Result.success(categoryQueryApplicationService.getAllUserCategories(request));
    }
}
