package com.zt.bookkeeping.ledger.domain.transactionStatement.entity;

import com.zt.bookkeeping.ledger.common.base.AbstractAgg;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerBudgetVO;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerStatusVO;
import com.zt.bookkeeping.ledger.domain.ledger.event.LedgerCreatedEvent;
import com.zt.bookkeeping.ledger.domain.ledger.event.LedgerUpdatedEvent;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CategoryVO {

    private String categoryNo;
    private String categoryName;
    private String categoryIcon;
    private Integer categoryLevel;
    private String categoryDesc;
    private String parentNo;
    private String userNo;


}
