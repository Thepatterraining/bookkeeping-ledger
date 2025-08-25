package com.zt.bookkeeping.ledger.application.ledger.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.bookkeeping.ledger.application.ledger.dto.LedgerListRes;
import com.zt.bookkeeping.ledger.application.ledger.dto.QueryLedgerListRequest;
import com.zt.bookkeeping.ledger.application.ledger.dto.QueryTransactionListRequest;
import com.zt.bookkeeping.ledger.application.ledger.dto.TransactionListRes;
import com.zt.bookkeeping.ledger.common.res.PageRes;
import com.zt.bookkeeping.ledger.domain.ledger.service.LedgerDomainService;
import com.zt.bookkeeping.ledger.infrastructure.db.LedgerMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.TransactionStatementMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.LedgerPO;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.TransactionStatementPO;
import com.zt.bookkeeping.ledger.infrastructure.util.LocalDateTimeUtil;
import com.zt.bookkeeping.ledger.infrastructure.util.UserContextHolder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransactionStatementQueryApplicationService {

    @Resource
    private TransactionStatementMapper transactionStatementMapper;

    private List<TransactionListRes> convertToLedgerListResList(List<TransactionStatementPO> po) {
        if (CollectionUtils.isEmpty(po)) {
            return Collections.emptyList();
        }
        return po.stream().map(this::convertToLedgerListRes).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private TransactionListRes convertToLedgerListRes(TransactionStatementPO po) {
        if (po == null) {
            return null;
        }
        TransactionListRes res = new TransactionListRes();
        res.setNo(po.getTransactionStatementNo());
        res.setCategoryName(po.getCategoryName());
        res.setDesc(po.getTransactionDesc());
        res.setAmount(po.getAmount());
        res.setTime(LocalDateTimeUtil.format(po.getTransactionTime()));
        return res;
    }

    public PageRes<TransactionListRes> getTransactionList(QueryTransactionListRequest request) {
        // 获取用户ID
        String userNo = UserContextHolder.getCurrentUserNo();
        // 查询用户收支列表
        LambdaQueryWrapper<TransactionStatementPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TransactionStatementPO::getCreateUser, userNo)
                .between(TransactionStatementPO::getTransactionTime, request.getStartDate(), request.getEndDate());
        Page<TransactionStatementPO> pageList = transactionStatementMapper.selectPage(Page.of(request.getPage(), request.getSize()), wrapper);

        // 组装返回
        PageRes<TransactionListRes> pageRes = new PageRes<>();
        pageRes.setPageNum(pageList.getCurrent());
        pageRes.setPageSize(pageList.getSize());
        pageRes.setTotal(pageList.getTotal());
        pageRes.setList(convertToLedgerListResList(pageList.getRecords()));
        return pageRes;
    }
}
