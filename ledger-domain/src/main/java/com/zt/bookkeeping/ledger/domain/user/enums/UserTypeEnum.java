package com.zt.bookkeeping.ledger.domain.user.enums;

import lombok.Getter;

@Getter
public enum UserTypeEnum {
    NORMAL(1, "普通用户"),
    VIP(2, "VIP用户"),
    OPERATION(3, "运营用户");

    private final int code;
    private final String label;

    UserTypeEnum(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public static UserTypeEnum fromCode(int code) {
        for (UserTypeEnum e : values()) {
            if (e.code == code) return e;
        }
        throw new IllegalArgumentException("未知状态码: " + code);
    }
}
