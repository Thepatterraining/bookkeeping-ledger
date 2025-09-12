package com.zt.bookkeeping.ledger.infrastructure.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zt.bookkeeping.ledger.common.base.AbstractPO;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@TableName("ledger_member")
@Getter
public class LedgerMemberPO extends AbstractPO {
    public LedgerMemberPO() {
        super();
    }

    private String ledgerNo;
    private String userNo;
    private Integer role;
    private LocalDateTime joinTime;
    private Integer status;

}
