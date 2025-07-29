package com.zt.bookkeeping.ledger.common.base;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class AbstractPO {

    private String createUser;
    private String updateUser;
    private Boolean isDeleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
