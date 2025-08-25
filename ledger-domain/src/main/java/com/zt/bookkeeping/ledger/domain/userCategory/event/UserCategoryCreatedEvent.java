package com.zt.bookkeeping.ledger.domain.userCategory.event;

import com.zt.bookkeeping.ledger.common.base.AbstractEvent;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.TransactionStatementAgg;
import com.zt.bookkeeping.ledger.domain.userCategory.entity.UserCategoryAgg;

public class UserCategoryCreatedEvent extends AbstractEvent<UserCategoryAgg> {

    public UserCategoryCreatedEvent(UserCategoryAgg data) {
        super(data, "userCategoryCreatedEvent");
    }
}
