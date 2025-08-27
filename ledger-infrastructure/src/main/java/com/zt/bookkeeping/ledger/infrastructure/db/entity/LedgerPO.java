package com.zt.bookkeeping.ledger.infrastructure.db.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zt.bookkeeping.ledger.common.base.AbstractPO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@SuperBuilder
@TableName("ledger")
@Getter
public class LedgerPO extends AbstractPO {

    public LedgerPO() {
        super();
    }

    private String ledgerNo;
    private String ledgerName;
    private String ownerNo;
    private Integer ledgerStatus;
    private String ledgerDesc;
    private String ledgerImage;

}
