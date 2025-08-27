package com.zt.bookkeeping.ledger.domain.userCategory.entity;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.zt.bookkeeping.ledger.common.base.AbstractAgg;
import com.zt.bookkeeping.ledger.domain.sysCategory.entity.SysCategoryAgg;
import com.zt.bookkeeping.ledger.domain.transactionStatement.event.TransactionStatementCreatedEvent;
import com.zt.bookkeeping.ledger.domain.userCategory.event.UserCategoryCreatedEvent;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/8/20
 * Time:10:32
 */
@Getter
@Setter
@Builder
public class UserCategoryAgg extends AbstractAgg {
    private Long id;
    private String categoryNo;
    private String categoryName;
    private String categoryIcon;
    private Integer categoryLevel;
    private String categoryDesc;
    private String parentNo;
    private String userNo;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<UserCategoryAgg> subCategories = new ArrayList<>();

    public void create() {
        LocalDateTime now = LocalDateTime.now();
        this.setCreateTime(now);
        this.setUpdateTime(now);

        // 注册 用户分类已创建事件
        registerDomainEvent(new UserCategoryCreatedEvent(this));
    }

    public void addSubCategory(UserCategoryAgg userCategoryAgg) {
        subCategories.add(userCategoryAgg);
    }

    public Boolean isParent() {
        return StringUtils.isBlank(parentNo);
    }

}
