package com.zt.bookkeeping.ledger.application.ledger.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryListRes {
    private String categoryName;
    private String categoryNo;
    private Integer categoryLevel;
    private String categoryDesc;
    private String categoryIcon;
}
