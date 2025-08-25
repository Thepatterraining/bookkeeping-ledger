package com.zt.bookkeeping.ledger.domain.sysCategory.entity;

import com.zt.bookkeeping.ledger.common.base.AbstractAgg;
import com.zt.bookkeeping.ledger.domain.userCategory.entity.UserCategoryAgg;
import lombok.Builder;
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
public class SysCategoryAgg extends AbstractAgg {
    private String categoryNo;
    private String categoryName;
    private String categoryIcon;
    private Integer categoryLevel;
    private String categoryDesc;
    private String parentNo;
    private Long id;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<UserCategoryAgg> subCategories = new ArrayList<>();
}
