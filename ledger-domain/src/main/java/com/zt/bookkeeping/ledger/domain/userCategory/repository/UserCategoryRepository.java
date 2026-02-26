package com.zt.bookkeeping.ledger.domain.userCategory.repository;

import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.CategoryVO;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.TransactionStatementAgg;
import com.zt.bookkeeping.ledger.domain.userCategory.entity.UserCategoryAgg;

import java.util.List;

public interface UserCategoryRepository {

    void insert(UserCategoryAgg userCategoryAgg);

    void update(UserCategoryAgg userCategoryAgg);

    UserCategoryAgg load(String categoryNo, String userNo);

    UserCategoryAgg load(String categoryNo);

    List<UserCategoryAgg> loadListByUserNo(String userNo, Integer categoryType);

    UserCategoryAgg findByNameAndUserNo(String name, String userNo);
}
