package com.zt.bookkeeping.ledger.infrastructure.listener;

import com.zt.bookkeeping.ledger.domain.ledger.event.LedgerCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Async
public class LedgerCreatedListener {

    @EventListener
    public void handle(LedgerCreatedEvent event) {
        log.info("生成账本成功：{} ", event.toString());
        // 写入登录日志

    }
}
