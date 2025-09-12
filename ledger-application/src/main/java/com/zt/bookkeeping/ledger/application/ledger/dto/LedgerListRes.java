package com.zt.bookkeeping.ledger.application.ledger.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LedgerListRes  {
    private String ledgerName;
    private String ledgerNo;
    private Integer ledgerStatus;
    private String ledgerDesc;
    private String ledgerImage;
    private String createTime;
    private String updateTime;
    private String joinTime;
    private String role;
}
