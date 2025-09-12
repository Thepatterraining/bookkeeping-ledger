package com.zt.bookkeeping.ledger.application.ledger.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.zt.bookkeeping.ledger.application.config.YearMonthDateDeserializer;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class UpdateLedgerBudgetRequest {
    private String ledgerNo;

    /**
     * 预算日期，支持年月格式（yyyy-MM）
     * 在反序列化时会自动设置为当月第一天
     */
    private LocalDate budgetDate;

    private BigDecimal budgetAmount;
}
