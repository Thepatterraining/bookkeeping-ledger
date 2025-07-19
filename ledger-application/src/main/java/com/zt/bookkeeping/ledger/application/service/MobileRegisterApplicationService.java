package com.zt.bookkeeping.ledger.application.service;

import com.zt.bookkeeping.ledger.domain.user.entity.UserAgg;
import com.zt.bookkeeping.ledger.domain.user.event.UserRegisteredEvent;
import com.zt.bookkeeping.ledger.domain.user.req.MobileRegisterRequest;
import com.zt.bookkeeping.ledger.domain.user.service.UserAggService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Desc:
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/7/15
 * Time:11:15
 */
@Service
@Slf4j
public class MobileRegisterApplicationService {

    @Resource
    private UserAggService userAggService;

    @Resource
    private ApplicationEventPublisher eventPublisher;

    public Long register(MobileRegisterRequest command) {
        // 校验验证码是否正确
        checkVerifyCode(command.getMobile(), command.getCode());

        // 2. 调用用户领域服务 判断能否注册用户
        userAggService.canRegister(command.getMobile());

        // 验证码正确 用户可以注册 生成用户聚合
        UserAgg userAgg = UserAgg.init(command.getMobile());

        // 3. 调用用户领域服务 注册用户
        userAggService.save(userAgg);

        // 4. 发送注册成功领域事件
        eventPublisher.publishEvent(new UserRegisteredEvent(userAgg.getId(), userAgg.getUsername(), null, LocalDateTime.now()));
        return userAgg.getId();
    }

    private void checkVerifyCode(String mobile, String verifyCode) {
        // 调用RPC接口来校验验证码

    }
}
