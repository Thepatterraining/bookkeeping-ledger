package com.zt.bookkeeping.ledger.infrastructure.gateway.rpc;

import com.zt.bookkeeping.ledger.common.api.UserService;
import com.zt.bookkeeping.ledger.domain.ledger.bo.UserInfoBO;
import com.zt.bookkeeping.ledger.domain.ledger.gateway.rpc.UserInfoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Desc:
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/9/11
 * Time:17:29
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @DubboReference()
    private UserService userService;

    public List<UserInfoBO> batchQueryUserInfo(List<String> userNoList) {
        // 构建请求信息

        // 发送请求

        // 构建返回信息
        return Collections.singletonList(new UserInfoBO());
    }

}
