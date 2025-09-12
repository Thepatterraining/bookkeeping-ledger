package com.zt.bookkeeping.ledger.infrastructure.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zt.bookkeeping.ledger.common.base.AbstractPO;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@TableName("invitation")
@Getter
public class InvitationPO extends AbstractPO {
    public InvitationPO() {
        super();
    }

    private String code;
    private String ledgerNo;
    private String inviterNo;
    private LocalDateTime expireTime;
    private Integer maxUseCount;
    private Integer usedCount;
    private Integer status;

}
