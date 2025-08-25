package com.zt.bookkeeping.ledger.infrastructure.db.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.zt.bookkeeping.ledger.common.base.AbstractPO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@TableName("user_category")
public class UserCategoryPO extends AbstractPO {

    private String userNo;
    private String categoryNo;
    private String categoryName;
    private String categoryIcon;
    private Integer categoryLevel;
    private String categoryDesc;
    private String parentNo;

}
