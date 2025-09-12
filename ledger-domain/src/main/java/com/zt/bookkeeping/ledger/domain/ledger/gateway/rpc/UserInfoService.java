package com.zt.bookkeeping.ledger.domain.ledger.gateway.rpc;

import com.zt.bookkeeping.ledger.domain.ledger.bo.UserInfoBO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Desc:
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/9/11
 * Time:17:29
 */
public interface UserInfoService  {

    List<UserInfoBO> batchQueryUserInfo(List<String> userNoList);
}
