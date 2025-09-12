package com.zt.bookkeeping.ledger.application.transaction.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionListRes {
    private String no;
    private String categoryName;
    private String amount;
    private String time;
    private String desc;
    private String categoryIcon;
    private Integer transactionType;
}
