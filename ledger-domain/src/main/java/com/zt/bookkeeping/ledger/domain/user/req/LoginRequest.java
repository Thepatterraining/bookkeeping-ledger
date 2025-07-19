package com.zt.bookkeeping.ledger.domain.user.req;

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
public class LoginRequest implements Serializable {
    private String username;
    private String password;
    private String mobile;
    private String code;
}
