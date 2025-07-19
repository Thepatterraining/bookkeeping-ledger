package com.zt.bookkeeping.ledger.domain.exception;

import com.zt.bookkeeping.ledger.common.enums.ResultCode;

/**
 * Desc:
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/7/14
 * Time:16:22
 */
public class UserLoginException extends DomainException {
    public UserLoginException(String message) {
        super(message, ResultCode.LOGIN_ERROR.getCode());
    }

    public UserLoginException(String message, Throwable cause) {
        super(message, cause, ResultCode.LOGIN_ERROR.getCode());
    }

    public UserLoginException(ResultCode resultCode) {
        super(resultCode);
    }
}

