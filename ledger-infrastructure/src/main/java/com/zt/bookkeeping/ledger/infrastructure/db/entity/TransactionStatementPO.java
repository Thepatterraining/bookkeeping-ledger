package com.zt.bookkeeping.ledger.infrastructure.db.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zt.bookkeeping.ledger.common.base.AbstractPO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@TableName("transaction_statement")
public class TransactionStatementPO extends AbstractPO {

    public TransactionStatementPO() {
        super();
    }

    private String transactionStatementNo;
    private String ledgerNo;
    private Integer amount;
    private Integer transactionType;
    private LocalDateTime transactionTime;
    private String transactionDesc;
    private Integer transactionStatus;
    private String categoryNo;
    private String categoryName;
    private Integer categoryType; // 1 系统 2 用户
    private String categoryIcon;

}
