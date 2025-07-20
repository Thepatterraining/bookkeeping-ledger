package com.zt.bookkeeping.ledger.application.ledger.service;

import com.zt.bookkeeping.ledger.application.ledger.dto.CreateLedgerRequest;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import com.zt.bookkeeping.ledger.domain.ledger.service.LedgerDomainService;
import com.zt.bookkeeping.ledger.domain.user.event.LedgerCreatedEvent;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class LedgerCommandApplicationService {

    @Resource
    private ApplicationEventPublisher eventPublisher;

    @Resource
    private LedgerDomainService ledgerDomainService;

    public String createLedger(CreateLedgerRequest request) {
        // 查询该账本是否已经存在 todo
        LedgerAgg ledger = ledgerDomainService.findByNameInUser(request.getLedgerName(), "");
        if (ledger != null) {
            return "Ledger already exists";
        }
        // 账本不存在则插入
        LedgerAgg ledgerAgg = new LedgerAgg();
        ledgerDomainService.insert(ledgerAgg);

        // 账本已创建事件
        eventPublisher.publishEvent(new LedgerCreatedEvent(ledgerAgg.getOwnerNo(), ledgerAgg.getLedgerName(), ledgerAgg.getLedgerNo(), LocalDateTime.now()));

        return ledgerAgg.getLedgerNo();
    }
}
