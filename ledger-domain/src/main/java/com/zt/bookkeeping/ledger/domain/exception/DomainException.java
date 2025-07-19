package com.zt.bookkeeping.ledger.domain.exception;

import com.zt.bookkeeping.ledger.common.enums.ResultCode;
import lombok.Getter;

/**
 * Desc:
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/7/14
 * Time:16:22
 */
@Getter
public class DomainException extends RuntimeException {

    private final int code;

    public DomainException(String message, int code) {
        super(message);
        this.code = code;
    }

    public DomainException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public DomainException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }
}

