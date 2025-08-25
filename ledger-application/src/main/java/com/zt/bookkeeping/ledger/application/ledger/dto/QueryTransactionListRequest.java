package com.zt.bookkeeping.ledger.application.ledger.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class QueryTransactionListRequest {
    private Integer page;
    private Integer size;
    private LocalDate startDate;
    private LocalDate endDate;
}
