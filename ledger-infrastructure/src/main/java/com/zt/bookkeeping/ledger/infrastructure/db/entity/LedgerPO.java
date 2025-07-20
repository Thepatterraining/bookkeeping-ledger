package com.zt.bookkeeping.ledger.infrastructure.db.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class LedgerPO {
    private Long id;
    private String ledgerNo;
    private String ledgerName;
    private String ownerNo;
    private Integer ledgerStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
