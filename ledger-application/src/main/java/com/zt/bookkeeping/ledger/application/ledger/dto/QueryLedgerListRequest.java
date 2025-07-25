package com.zt.bookkeeping.ledger.application.ledger.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryLedgerListRequest {
    private Integer page;
    private Integer size;
}
