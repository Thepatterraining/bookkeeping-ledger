package com.zt.bookkeeping.ledger.domain.invitation.entity;

import lombok.Getter;

/**
 * Desc:
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/9/1
 * Time:17:07
 */
@Getter
public class InvitationCodeVO {
    private String code;
    public InvitationCodeVO(String code) {
        if (code == null || code.length() != 8) {
            throw new IllegalArgumentException("邀请码长度必须为8位");
        }
        this.code = code;
    }

}
