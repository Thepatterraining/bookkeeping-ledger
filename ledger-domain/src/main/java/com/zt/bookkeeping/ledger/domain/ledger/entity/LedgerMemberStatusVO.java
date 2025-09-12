package com.zt.bookkeeping.ledger.domain.ledger.entity;

import lombok.Getter;

public class LedgerMemberStatusVO {

    private final LedgerMemberStatusEnum status;

    public static final LedgerMemberStatusVO NORMAL = new LedgerMemberStatusVO(LedgerMemberStatusEnum.NORMAL);
    public static final LedgerMemberStatusVO EXITED = new LedgerMemberStatusVO(LedgerMemberStatusEnum.EXITED);

    private LedgerMemberStatusVO(LedgerMemberStatusEnum status) {
        this.status = status;
    }

    public static LedgerMemberStatusVO of(int code) {
        return new LedgerMemberStatusVO(LedgerMemberStatusEnum.fromCode(code));
    }

    public int getCode() {
        return status.getCode();
    }

    public LedgerMemberStatusEnum getStatusEnum() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof LedgerMemberStatusVO that) && this.status == that.status;
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
    public enum LedgerMemberStatusEnum {
        NORMAL(1, "正常"),
        EXITED(2, "已退出");

        private final int code;
        private final String label;

        LedgerMemberStatusEnum(int code, String label) {
            this.code = code;
            this.label = label;
        }

        public static LedgerMemberStatusEnum fromCode(int code) {
            for (LedgerMemberStatusEnum e : values()) {
                if (e.code == code) return e;
            }
            throw new IllegalArgumentException("未知状态码: " + code);
        }
    }
}
