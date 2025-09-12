package com.zt.bookkeeping.ledger.domain.ledger.entity;

import lombok.Getter;

public class LedgerMemberRoleVO {

    private final LedgerMemberRoleEnum status;

    public static final LedgerMemberRoleVO ADMIN = new LedgerMemberRoleVO(LedgerMemberRoleEnum.ADMIN);
    public static final LedgerMemberRoleVO MEMBER = new LedgerMemberRoleVO(LedgerMemberRoleEnum.MEMBER);

    private LedgerMemberRoleVO(LedgerMemberRoleEnum status) {
        this.status = status;
    }

    public static LedgerMemberRoleVO of(int code) {
        return new LedgerMemberRoleVO(LedgerMemberRoleEnum.fromCode(code));
    }

    public int getCode() {
        return status.getCode();
    }

    public String getLabel() {
        return status.getLabel();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof LedgerMemberRoleVO that) && this.status == that.status;
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }

    @Override
    public String toString() {
        return "LedgerMemberRoleVO{" +
                "status =" + status.getLabel() +
                '}';
    }

    @Getter
    public enum LedgerMemberRoleEnum {
        ADMIN(1, "管理员"),
        MEMBER(2, "成员");

        private final int code;
        private final String label;

        LedgerMemberRoleEnum(int code, String label) {
            this.code = code;
            this.label = label;
        }

        public static LedgerMemberRoleEnum fromCode(int code) {
            for (LedgerMemberRoleEnum e : values()) {
                if (e.code == code) return e;
            }
            throw new IllegalArgumentException("未知状态码: " + code);
        }
    }
}
