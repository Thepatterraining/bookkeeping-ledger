package com.zt.bookkeeping.ledger.infrastructure.db.entity;


import com.zt.bookkeeping.ledger.common.base.AbstractPO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class LedgerPO extends AbstractPO {
    private Long id;
    private String ledgerNo;
    private String ledgerName;
    private String ownerNo;
    private Integer ledgerStatus;
    private String ledgerDesc;
    private String ledgerImage;

}
