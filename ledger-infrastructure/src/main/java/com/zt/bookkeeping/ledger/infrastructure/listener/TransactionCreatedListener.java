package com.zt.bookkeeping.ledger.infrastructure.listener;

import com.zt.bookkeeping.ledger.domain.ledger.event.LedgerCreatedEvent;
import com.zt.bookkeeping.ledger.domain.transactionStatement.event.TransactionStatementCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Async
public class TransactionCreatedListener {

    @EventListener
    public void handle(TransactionStatementCreatedEvent event) {
        log.info("记账成功：{} ", event.toString());
        // 修改预算信息


    }
}
