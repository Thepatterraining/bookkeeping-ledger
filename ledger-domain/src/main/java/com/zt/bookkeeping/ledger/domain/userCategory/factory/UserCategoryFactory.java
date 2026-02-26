package com.zt.bookkeeping.ledger.domain.userCategory.factory;

import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerBudgetVO;
import com.zt.bookkeeping.ledger.domain.userCategory.entity.UserCategoryAgg;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface UserCategoryFactory {

    UserCategoryAgg createUserCategory(String categoryName, String userNo, String parentCategoryNo,
            String description, String categoryIcon, Integer categoryLevel, Integer categoryType);

}
