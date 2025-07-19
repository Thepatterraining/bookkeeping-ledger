package com.zt.bookkeeping.ledger.starter.controller;

import com.zt.bookkeeping.ledger.application.service.MobileRegisterApplicationService;
import com.zt.bookkeeping.ledger.domain.user.req.MobileRegisterRequest;
import com.zt.bookkeeping.ledger.infrastructure.common.Result;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Desc:
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/7/9
 * Time:17:28
 */
@RequestMapping("/user/register")
@RestController
public class RegisterController {

    @Resource
    private MobileRegisterApplicationService mobileRegisterApplicationService;

    @PostMapping("/mobile")
    public Result<Long> mobileRegister(@Valid @RequestBody MobileRegisterRequest request){
        Long registerRes = mobileRegisterApplicationService.register(request);
        return Result.success("注册成功", registerRes);
    }
}
