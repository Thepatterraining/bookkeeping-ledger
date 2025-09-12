package com.zt.bookkeeping.ledger.application.category.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryListRes {
    private String categoryName;
    private String categoryNo;
    private Integer categoryLevel;
    private String categoryDesc;
    private String categoryIcon;
    private List<CategoryListRes> subCategoryList;
}
