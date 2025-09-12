package com.zt.bookkeeping.ledger.domain.invitation.entity;

import lombok.Getter;

public class InvitationStatusVO {

    private final InvitationStatusEnum status;

    public static final InvitationStatusVO ACTIVE = new InvitationStatusVO(InvitationStatusEnum.ACTIVE);
    public static final InvitationStatusVO EXPIRED = new InvitationStatusVO(InvitationStatusEnum.EXPIRED);
    public static final InvitationStatusVO USED = new InvitationStatusVO(InvitationStatusEnum.USED);

    private InvitationStatusVO(InvitationStatusEnum status) {
        this.status = status;
    }

    public static InvitationStatusVO of(int code) {
        return new InvitationStatusVO(InvitationStatusEnum.fromCode(code));
    }

    public Boolean isValid() {
        return status == InvitationStatusEnum.ACTIVE;
    }

    public int getCode() {
        return status.getCode();
    }

    public InvitationStatusEnum getStatusEnum() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof InvitationStatusVO that) && this.status == that.status;
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }

    @Override
    public String toString() {
        return "InvitationStatusVO{" +
                "status =" + status.getLabel() +
                '}';
    }

    @Getter
    // 邀请状态值对象
    public enum InvitationStatusEnum {
        ACTIVE(1, "有效"),
        EXPIRED(2, "已过期"),
        USED(3, "已使用");

        private final int code;
        private final String label;

        InvitationStatusEnum(int code, String description) {
            this.code = code;
            this.label = description;
        }

        public static InvitationStatusEnum fromCode(int code) {
            for (InvitationStatusEnum e : values()) {
                if (e.code == code) return e;
            }
            throw new IllegalArgumentException("未知状态码: " + code);
        }
    }

}
