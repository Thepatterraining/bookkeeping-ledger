package com.zt.bookkeeping.ledger.domain.sysCategory.repository;

import com.zt.bookkeeping.ledger.domain.sysCategory.entity.SysCategoryAgg;
import com.zt.bookkeeping.ledger.domain.userCategory.entity.UserCategoryAgg;

import java.util.List;

public interface SysCategoryRepository {

    void insert(SysCategoryAgg sysCategoryAgg);

    void update(SysCategoryAgg sysCategoryAgg);

    SysCategoryAgg load(String categoryNo);

    List<SysCategoryAgg> loadAll();
}
