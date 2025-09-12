package com.zt.bookkeeping.ledger.application.ledger.dto;

import lombok.Data;

/**
 * 账本成员列表响应
 */
@Data
public class LedgerMemberListRes {

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 角色
     */
    private String role;

    /**
     * 加入时间
     */
    private String joinTime;
}
