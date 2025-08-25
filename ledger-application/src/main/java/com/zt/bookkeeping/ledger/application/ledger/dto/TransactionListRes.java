package com.zt.bookkeeping.ledger.application.ledger.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionListRes {
    private String no;
    private String categoryName;
    private Integer amount;
    private String time;
    private String desc;
    private String categoryIcon;
}
