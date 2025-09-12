package com.zt.bookkeeping.ledger.common.enums;

/**
 * 响应状态码枚举
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/7/9
 * Time:17:35
 */
public enum PersistenceTypeEnum {

    // 成功
    INSERT(1, "插入"),
    UPDATE(2, "更新"),
    DELETE(3, "删除");

    private final Integer code;
    private final String message;

    PersistenceTypeEnum(Integer code, String message) {
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

