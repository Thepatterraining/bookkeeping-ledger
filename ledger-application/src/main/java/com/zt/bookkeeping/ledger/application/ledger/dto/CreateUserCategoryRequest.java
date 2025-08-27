package com.zt.bookkeeping.ledger.application.ledger.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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
