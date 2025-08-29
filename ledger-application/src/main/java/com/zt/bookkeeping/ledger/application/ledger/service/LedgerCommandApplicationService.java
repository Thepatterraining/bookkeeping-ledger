package com.zt.bookkeeping.ledger.application.ledger.service;

import com.zt.bookkeeping.ledger.application.ledger.dto.CreateLedgerRequest;
import com.zt.bookkeeping.ledger.application.ledger.dto.UpdateLedgerBudgetRequest;
import com.zt.bookkeeping.ledger.application.ledger.dto.UpdateLedgerRequest;
import com.zt.bookkeeping.ledger.common.base.DomainEvent;
import com.zt.bookkeeping.ledger.common.enums.ResultCode;
import com.zt.bookkeeping.ledger.domain.exception.AggNotExistsException;
import com.zt.bookkeeping.ledger.domain.exception.DomainException;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerBudgetVO;
import com.zt.bookkeeping.ledger.domain.ledger.event.LedgerUpdatedEvent;
import com.zt.bookkeeping.ledger.domain.ledger.factory.LedgerFactory;
import com.zt.bookkeeping.ledger.domain.ledger.service.LedgerDomainService;
import com.zt.bookkeeping.ledger.domain.ledger.event.LedgerCreatedEvent;
import com.zt.bookkeeping.ledger.infrastructure.util.UserContextHolder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class LedgerCommandApplicationService {

    @Resource
    private ApplicationEventPublisher eventPublisher;

    @Resource
    private LedgerDomainService ledgerDomainService;

    @Resource
    private LedgerFactory ledgerFactory;

    public String createLedger(CreateLedgerRequest request) {
        // 获取用户ID
        String userNo = UserContextHolder.getCurrentUserNo();

        // 查询该账本是否已经存在
        LedgerAgg ledger = ledgerDomainService.findByNameInUser(request.getLedgerName(), userNo);
        if (ledger != null) {
            throw new AggNotExistsException(ResultCode.LEDGER_ALREADY_EXISTS);
        }
        // 账本不存在则创建
        LedgerAgg ledgerAgg = ledgerFactory.createLedgerAgg(request.getLedgerName(), userNo, request.getLedgerDesc(),
                request.getLedgerImage());
        ledgerAgg.create();

        // 插入数据库
        ledgerDomainService.save(ledgerAgg);

        // 获取注册的事件进行发布
        List<DomainEvent> domainEventList = ledgerAgg.getDomainEvents();
        domainEventList.forEach(event -> eventPublisher.publishEvent(event));

        // 返回账本编号
        return ledgerAgg.getLedgerNo();
    }

    public void updateLedger(UpdateLedgerRequest request) {
        // 查询该账本是否已经存在
        LedgerAgg ledgerAgg = ledgerDomainService.findByNo(request.getLedgerNo());
        if (ledgerAgg == null) {
            throw new AggNotExistsException(ResultCode.LEDGER_NOT_FOUND);
        }
        // 账本存在则更新
        ledgerAgg.save(request.getLedgerName(), request.getLedgerDesc(), request.getLedgerImage());
        ledgerDomainService.save(ledgerAgg);

        // 获取注册的事件进行发布
        List<DomainEvent> domainEventList = ledgerAgg.getDomainEvents();
        eventPublisher.publishEvent(domainEventList);
    }

    public void updateLedgerBudget(UpdateLedgerBudgetRequest request) {
        // 查询该账本是否已经存在
        LedgerAgg ledgerAgg = ledgerDomainService.findByNo(request.getLedgerNo());
        if (ledgerAgg == null) {
            throw new AggNotExistsException(ResultCode.LEDGER_NOT_FOUND);
        }
        // 账本存在则更新
        LedgerBudgetVO ledgerBudget = ledgerFactory.createLedgerBudget(request.getLedgerNo(), request.getBudgetAmount(), request.getBudgetDate());
        ledgerAgg.updateBudget(ledgerBudget);
        ledgerDomainService.save(ledgerAgg);

        // 获取注册的事件进行发布
        List<DomainEvent> domainEventList = ledgerAgg.getDomainEvents();
        eventPublisher.publishEvent(domainEventList);
    }
}
