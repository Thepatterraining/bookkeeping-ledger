package com.zt.bookkeeping.ledger.application.ledger.service;

import com.zt.bookkeeping.ledger.application.ledger.dto.CreateLedgerRequest;
import com.zt.bookkeeping.ledger.application.ledger.dto.CreateTransactionRequest;
import com.zt.bookkeeping.ledger.application.ledger.dto.CreateUserCategoryRequest;
import com.zt.bookkeeping.ledger.application.ledger.dto.UpdateLedgerRequest;
import com.zt.bookkeeping.ledger.common.base.DomainEvent;
import com.zt.bookkeeping.ledger.common.enums.ResultCode;
import com.zt.bookkeeping.ledger.domain.exception.AggNotExistsException;
import com.zt.bookkeeping.ledger.domain.exception.DomainException;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import com.zt.bookkeeping.ledger.domain.ledger.factory.LedgerFactory;
import com.zt.bookkeeping.ledger.domain.ledger.factory.TransactionStatementFactory;
import com.zt.bookkeeping.ledger.domain.ledger.service.LedgerDomainService;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.CategoryVO;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.TransactionStatementAgg;
import com.zt.bookkeeping.ledger.domain.transactionStatement.service.TransactionStatementDomainService;
import com.zt.bookkeeping.ledger.infrastructure.util.UserContextHolder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TransactionStatementCommandApplicationService {

    @Resource
    private ApplicationEventPublisher eventPublisher;

    @Resource
    private TransactionStatementDomainService transactionStatementDomainService;

    @Resource
    private LedgerDomainService ledgerDomainService;

    @Resource
    private TransactionStatementFactory transactionStatementFactory;

    public String createTransactionStatement(CreateTransactionRequest request) {
        // 获取用户ID
        String userNo = UserContextHolder.getCurrentUserNo();

        // 查询账本是否存在
        LedgerAgg ledger = ledgerDomainService.findByNo(request.getLedgerNo());
        if (ledger == null) {
            throw new AggNotExistsException(ResultCode.LEDGER_NOT_FOUND);
        }
        // 判断用户是否有权限
        if (!ledger.checkUserPermission(userNo)) {
            throw new DomainException(ResultCode.UNAUTHORIZED);
        }

        // 账本存在则创建交易流水
        TransactionStatementAgg transactionStatementAgg = transactionStatementFactory.createTransactionStatementAgg(request.getLedgerNo(),
                request.getAmount(), request.getTransactionType(), request.getTransactionDesc(),
                request.getCategoryNo(), request.getCategoryType(), userNo);
        transactionStatementAgg.create();

        // 插入数据库
        transactionStatementDomainService.save(transactionStatementAgg);

        // 获取注册的事件进行发布
        List<DomainEvent> domainEventList = transactionStatementAgg.getDomainEvents();
        domainEventList.forEach(event -> eventPublisher.publishEvent(event));

        // 返回账本编号
        return transactionStatementAgg.getTransactionStatementNo();
    }

}
