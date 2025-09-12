package com.zt.bookkeeping.ledger.application.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserCategoryRequest {

    @NotNull
    private Integer categoryLevel;

    private String transactionDesc;

    @NotBlank
    private String categoryName;

    @NotBlank
    private String categoryIcon;

    private String parentNo;

}
