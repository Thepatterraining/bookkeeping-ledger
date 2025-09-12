package com.zt.bookkeeping.ledger.domain.transactionStatement.entity;

import com.zt.bookkeeping.ledger.common.base.AbstractAgg;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerBudgetVO;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerStatusVO;
import com.zt.bookkeeping.ledger.domain.ledger.event.LedgerCreatedEvent;
import com.zt.bookkeeping.ledger.domain.ledger.event.LedgerUpdatedEvent;
import com.zt.bookkeeping.ledger.domain.transactionStatement.event.TransactionStatementCreatedEvent;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionStatementAgg extends AbstractAgg {
    private Long id;
    private String transactionStatementNo;
    private String ledgerNo;
    private Integer amount;
    private TransactionTypeVO transactionType;
    private LocalDateTime transactionTime;
    private String transactionDesc;
    private Integer transactionStatus;
    // 分类信息快照
    private CategorySnapshotVO categorySnapshot;
    // 分类信息引用
    private CategoryVO category;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private Boolean deleted;

    public void create() {
        LocalDateTime now = LocalDateTime.now();
        this.setCreateTime(now);
        this.setUpdateTime(now);
        this.setTransactionTime(now);

        // 注册交易流水已创建事件
        registerDomainEvent(new TransactionStatementCreatedEvent(this));
    }

    public void delete() {
        deleted = true;
        updateTime = LocalDateTime.now();
        // 注册交易流水已删除事件
        registerDomainEvent(new TransactionStatementCreatedEvent(this));
    }
}
