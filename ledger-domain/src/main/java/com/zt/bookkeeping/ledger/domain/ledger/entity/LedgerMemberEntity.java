package com.zt.bookkeeping.ledger.domain.ledger.entity;

import com.zt.bookkeeping.ledger.common.base.AbstractAgg;
import com.zt.bookkeeping.ledger.common.enums.PersistenceTypeEnum;
import com.zt.bookkeeping.ledger.domain.ledger.event.LedgerCreatedEvent;
import com.zt.bookkeeping.ledger.domain.ledger.event.LedgerUpdatedEvent;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
public class LedgerMemberEntity extends AbstractAgg {
    private Long id;
    private String ledgerNo;
    private String userNo;
    private LedgerMemberRoleVO role;
    private LocalDateTime joinTime;
    private LedgerMemberStatusVO status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private PersistenceTypeEnum persistenceType;
    private Boolean isDeleted;

    public void delete() {
        isDeleted = true;
    }
}
