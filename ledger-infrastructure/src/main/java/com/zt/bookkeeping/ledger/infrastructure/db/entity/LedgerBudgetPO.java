package com.zt.bookkeeping.ledger.infrastructure.db.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zt.bookkeeping.ledger.common.base.AbstractPO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@TableName("ledger_budget")
public class LedgerBudgetPO extends AbstractPO {

    public LedgerBudgetPO() {
        super();
    }

    private String ledgerNo;
    private Integer budgetAmount;
    private Integer usedAmount;
    private Integer remainedAmount;
    private LocalDate budgetDate;
}
