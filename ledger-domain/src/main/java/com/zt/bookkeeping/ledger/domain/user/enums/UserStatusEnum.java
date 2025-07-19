package com.zt.bookkeeping.ledger.domain.user.enums;

import lombok.Getter;

@Getter
public enum UserStatusEnum {
    ACTIVE(1, "启用"),
    FROZEN(2, "冻结"),
    DELETED(3, "注销");

    private final int code;
    private final String label;

    UserStatusEnum(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public static UserStatusEnum fromCode(int code) {
        for (UserStatusEnum e : values()) {
            if (e.code == code) return e;
        }
        throw new IllegalArgumentException("未知状态码: " + code);
    }
}
