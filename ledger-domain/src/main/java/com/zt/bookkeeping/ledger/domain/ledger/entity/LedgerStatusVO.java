package com.zt.bookkeeping.ledger.domain.ledger.entity;

import lombok.Getter;

public class LedgerStatusVO {

    private final LedgerStatusEnum status;

    public static final LedgerStatusVO NORMAL = new LedgerStatusVO(LedgerStatusEnum.NORMAL);
    public static final LedgerStatusVO FROZEN = new LedgerStatusVO(LedgerStatusEnum.FROZEN);
    public static final LedgerStatusVO DELETED = new LedgerStatusVO(LedgerStatusEnum.DELETED);

    private LedgerStatusVO(LedgerStatusEnum status) {
        this.status = status;
    }

    public static LedgerStatusVO of(int code) {
        return new LedgerStatusVO(LedgerStatusEnum.fromCode(code));
    }

    public int getCode() {
        return status.getCode();
    }

    public String getLabel() {
        return status.getLabel();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof LedgerStatusVO that) && this.status == that.status;
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }

    @Override
    public String toString() {
        return "LedgerStatus{" +
                "status =" + status.getLabel() +
                '}';
    }

    @Getter
    public enum LedgerStatusEnum {
        NORMAL(1, "正常"),
        FROZEN(2, "冻结"),
        DELETED(3, "注销");

        private final int code;
        private final String label;

        LedgerStatusEnum(int code, String label) {
            this.code = code;
            this.label = label;
        }

        public static LedgerStatusEnum fromCode(int code) {
            for (LedgerStatusEnum e : values()) {
                if (e.code == code) return e;
            }
            throw new IllegalArgumentException("未知状态码: " + code);
        }
    }
}
