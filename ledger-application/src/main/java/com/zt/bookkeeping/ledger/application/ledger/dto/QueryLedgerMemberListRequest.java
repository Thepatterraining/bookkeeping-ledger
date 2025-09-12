package com.zt.bookkeeping.ledger.application.ledger.dto;

import lombok.Data;

/**
 * 查询账本成员列表请求
 */
@Data
public class QueryLedgerMemberListRequest {

    /**
     * 账本编号
     */
    private String ledgerNo;
}
