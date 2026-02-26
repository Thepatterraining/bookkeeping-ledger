package com.zt.bookkeeping.ledger.application.ledger.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class QueryCategoryListRequest implements Serializable {
    private Integer categoryType;
    private Integer page;
    private Integer size;
}
