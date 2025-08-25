package com.zt.bookkeeping.ledger.domain.transactionStatement.entity;

import lombok.Getter;

import java.util.Objects;

/**
 * Desc:
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/8/20
 * Time:16:20
 */
@Getter
public class CategoryInfo {

    private String categoryNo;

    private String categoryName;

    private String categoryIcon;

    public CategoryInfo(String name, String no, String icon) {
        this.categoryName = Objects.requireNonNull(name, "分类名称不能为空");
        this.categoryNo = no;
        this.categoryIcon = icon;
    }

    // 创建系统分类信息
    public static CategoryInfo createSystem(String name, String no, String icon) {
        return new CategoryInfo(name, no, icon);
    }

    // 创建用户分类信息
    public static CategoryInfo createUser(String name, String no, String icon) {
        validateUserCategory(name);
        return new CategoryInfo(name, no, icon);
    }

    private static void validateUserCategory(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("用户分类名称不能为空");
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException("分类名称不能超过50个字符");
        }
    }
}
