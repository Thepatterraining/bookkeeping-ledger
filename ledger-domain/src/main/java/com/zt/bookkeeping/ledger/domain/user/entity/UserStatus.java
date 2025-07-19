package com.zt.bookkeeping.ledger.domain.user.entity;

import com.zt.bookkeeping.ledger.domain.user.enums.UserStatusEnum;

public class UserStatus {
    private final UserStatusEnum status;

    public static final UserStatus ACTIVE = new UserStatus(UserStatusEnum.ACTIVE);
    public static final UserStatus FROZEN = new UserStatus(UserStatusEnum.FROZEN);
    public static final UserStatus DELETED = new UserStatus(UserStatusEnum.DELETED);

    private UserStatus(UserStatusEnum status) {
        this.status = status;
    }

    public static UserStatus of(int code) {
        return new UserStatus(UserStatusEnum.fromCode(code));
    }

    public int getCode() {
        return status.getCode();
    }

    public UserStatusEnum getStatusEnum() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof UserStatus that) && this.status == that.status;
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }

    public boolean canLogin() {
        return status.equals(UserStatusEnum.ACTIVE);
    }

    @Override
    public String toString() {
        return "UserStatus{" +
                "status =" + status.getLabel() +
                '}';
    }

}
