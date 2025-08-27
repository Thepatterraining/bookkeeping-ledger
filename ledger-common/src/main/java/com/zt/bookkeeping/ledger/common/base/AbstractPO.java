package com.zt.bookkeeping.ledger.common.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
public abstract class AbstractPO {

    public AbstractPO() {

    }

    @TableId(type = IdType.AUTO)
    protected Long id;
    protected String createUser;
    protected String updateUser;
    protected Boolean isDeleted;
    protected LocalDateTime createTime;
    protected LocalDateTime updateTime;

}
