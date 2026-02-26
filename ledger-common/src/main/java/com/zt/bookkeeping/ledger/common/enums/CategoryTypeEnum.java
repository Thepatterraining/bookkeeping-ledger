package com.zt.bookkeeping.ledger.common.enums;

/**
 * 响应状态码枚举
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/7/9
 * Time:17:35
 */
public enum CategoryTypeEnum {

    // 成功
    SYS(1, "系统"),
    USER(2, "用户");

    private final Integer code;
    private final String message;

    CategoryTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

