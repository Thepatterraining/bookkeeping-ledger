package com.zt.bookkeeping.ledger.infrastructure.db.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zt.bookkeeping.ledger.common.base.AbstractPO;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@TableName("ledger")
public class LedgerPO extends AbstractPO {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String ledgerNo;
    private String ledgerName;
    private String ownerNo;
    private Integer ledgerStatus;
    private String ledgerDesc;
    private String ledgerImage;

}
