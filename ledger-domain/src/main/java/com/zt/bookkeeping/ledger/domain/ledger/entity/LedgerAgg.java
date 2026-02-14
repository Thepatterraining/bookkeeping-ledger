package com.zt.bookkeeping.ledger.domain.ledger.entity;

import com.zt.bookkeeping.ledger.common.base.AbstractAgg;
import com.zt.bookkeeping.ledger.domain.ledger.event.LedgerCreatedEvent;
import com.zt.bookkeeping.ledger.domain.ledger.event.LedgerDeletedEvent;
import com.zt.bookkeeping.ledger.domain.ledger.event.LedgerUpdatedEvent;
import com.zt.bookkeeping.ledger.domain.ledger.event.UserJoinedLedgerEvent;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.TransactionTypeVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class LedgerAgg extends AbstractAgg {
    private Long id;
    private String ledgerNo;
    private String ledgerName;
    private String ownerNo;
    private LedgerStatusVO ledgerStatus;
    private String ledgerDesc;
    private String ledgerImage;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Boolean isDeleted;

    // 最新的一个预算信息
    private LedgerBudgetVO lastLedgerBudget;

    // 最新的一个汇总信息
    private LedgerSummaryVO lastLedgerSummary;

    // 账本成员列表
    private Set<LedgerMemberEntity> memberSet;

    // 插入成员列表
    public void addMember(LedgerMemberEntity member) {
        if (memberSet == null) {
            memberSet = new HashSet<>();
        }
        memberSet.add(member);
        // 创建用户加入账本事件
        registerDomainEvent(new UserJoinedLedgerEvent(this));
    }

    public void delete() {
        if (memberSet != null) {
            memberSet.forEach(LedgerMemberEntity::delete);
        }
        if (lastLedgerBudget != null) {
            lastLedgerBudget.delete();
        }
        isDeleted = true;
        // 删除账本事件
        registerDomainEvent(new LedgerDeletedEvent(this));
    }

    public void deleteTransaction(Integer amount) {
        // 增加预算
        lastLedgerBudget.increase(amount);
    }

    public void transaction(Integer amount, TransactionTypeVO transactionType) {
        //判断收入 or 支出
        if (transactionType.isExpenditure()) {
            // 支出，减少预算
            lastLedgerBudget.reduce(amount);
        }
    }

    public void save(String ledgerName, String ledgerDesc, String ledgerImage) {
        updateSelf(ledgerName, ledgerDesc, ledgerImage);

        // 创建账本更新事件
        registerDomainEvent(new LedgerUpdatedEvent(this));
    }

    public void updateBudget(LedgerBudgetVO ledgerBudget) {
        lastLedgerBudget = ledgerBudget;
    }

    private void updateSelf(String ledgerName, String ledgerDesc, String ledgerImage) {
        this.ledgerName = ledgerName;
        this.ledgerDesc = ledgerDesc;
        this.ledgerImage = ledgerImage;
    }

    public void create() {
        ledgerStatus = LedgerStatusVO.NORMAL;
        createTime = LocalDateTime.now();
        updateTime = createTime;

        // 注册账本已创建事件
        registerDomainEvent(new LedgerCreatedEvent(this));
    }

    public Boolean checkUserPermission(String userNo) {
        return ownerNo.equals(userNo);
    }

    public Boolean hasViewPermission(String userNo) {
        return memberSet.stream().anyMatch(member -> member.getUserNo().equals(userNo));
    }
}
