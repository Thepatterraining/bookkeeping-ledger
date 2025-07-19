package com.zt.bookkeeping.ledger.starter.controller;

import com.zt.bookkeeping.ledger.application.service.UserLoginApplicationService;
import com.zt.bookkeeping.ledger.application.service.UserMobileLoginApplicationService;
import com.zt.bookkeeping.ledger.domain.user.req.LoginRequest;
import com.zt.bookkeeping.ledger.domain.user.res.LoginRes;
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
@RequestMapping("/user/login")
@RestController
public class LoginController {

    @Resource
    private UserLoginApplicationService userLoginApplicationService;

    @Resource
    private UserMobileLoginApplicationService userMobileLoginApplicationService;

    @PostMapping("/password")
    public Result<LoginRes> pwdLogin(@Valid @RequestBody LoginRequest request){
        LoginRes loginRes = userLoginApplicationService.login(request);
        return Result.success("登录成功", loginRes);
    }

    @PostMapping("/mobile")
    public Result<LoginRes> mobileLogin(@Valid @RequestBody LoginRequest request){
        LoginRes loginRes = userMobileLoginApplicationService.login(request);
        return Result.success("登录成功", loginRes);
    }

}
