package com.zt.bookkeeping.ledger.domain.user.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Desc:
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/7/10
 * Time:20:13
 */
@Getter
@Setter
@Builder
public class LoginRes implements Serializable {
    private String token;
    private String username;
    private String mobile;
    private String email;
    private String userNo;
}
