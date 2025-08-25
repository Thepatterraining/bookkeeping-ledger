package com.zt.bookkeeping.ledger.domain.transactionStatement.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class CategorySnapshotVO {

    private String categoryNo;
    private String categoryName;
    private Integer sourceType; // 系统分类/用户分类

    public CategorySnapshotVO(String name, String no, Integer type) {
        this.categoryName = Objects.requireNonNull(name, "分类名称不能为空");
        this.categoryNo = no;
        this.sourceType = type;
    }

}
