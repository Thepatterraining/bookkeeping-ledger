package com.zt.bookkeeping.ledger.infrastructure.listener;

import com.zt.bookkeeping.ledger.domain.user.event.UserLoggedInEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Async
public class LoginLogListener {

    @EventListener
    public void handle(UserLoggedInEvent event) {
        log.info("写入登录日志。用户登录事件：{} ", event.toString());
        // 写入登录日志

    }
}
