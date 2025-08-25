package com.zt.bookkeeping.ledger.domain.ledger.entity;

import com.zt.bookkeeping.ledger.common.base.AbstractAgg;
import com.zt.bookkeeping.ledger.domain.ledger.event.LedgerCreatedEvent;
import com.zt.bookkeeping.ledger.domain.ledger.event.LedgerUpdatedEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

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

    // 最新的一个预算信息
    private LedgerBudgetVO lastLedgerBudget;

    public void save(String ledgerName, String ledgerDesc, String ledgerImage) {
        updateSelf(ledgerName, ledgerDesc, ledgerImage);

        // 创建账本更新事件
        registerDomainEvent(new LedgerUpdatedEvent(this));
    }

    public void saveBudget(LedgerBudgetVO ledgerBudget) {

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
}
