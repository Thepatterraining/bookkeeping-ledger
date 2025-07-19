package com.zt.bookkeeping.ledger.domain.user.entity;

import com.zt.bookkeeping.ledger.domain.user.enums.UserTypeEnum;

public class UserType {

    private final UserTypeEnum type;

    public static final UserType NORMAL = new UserType(UserTypeEnum.NORMAL);
    public static final UserType VIP = new UserType(UserTypeEnum.VIP);
    public static final UserType OPERATION = new UserType(UserTypeEnum.OPERATION);

    private UserType(UserTypeEnum type) {
        this.type = type;
    }

    public static UserType of(int code) {
        return new UserType(UserTypeEnum.fromCode(code));
    }

    public int getCode() {
        return type.getCode();
    }

    public UserTypeEnum getTypeEnum() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof UserType that) && this.type == that.type;
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }
}
