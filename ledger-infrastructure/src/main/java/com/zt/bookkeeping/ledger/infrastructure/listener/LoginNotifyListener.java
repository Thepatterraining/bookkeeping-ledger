package com.zt.bookkeeping.ledger.infrastructure.listener;

import com.zt.bookkeeping.ledger.domain.user.event.UserLoggedInEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Async
public class LoginNotifyListener {

    @EventListener
    public void handle(UserLoggedInEvent event) {
        log.info("发送登录成功消息。用户登录事件：{} ", event.toString());
        // 发送登录成功消息

    }
}
