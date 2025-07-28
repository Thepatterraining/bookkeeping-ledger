package com.zt.bookkeeping.ledger.common.base;

import java.util.List;

public class AbstractAgg {
    private List<DomainEvent> domainEvents;

    public void registerDomainEvent(DomainEvent domainEvent) {
        this.domainEvents.add(domainEvent);
    }

    public List<DomainEvent>  getDomainEvents() {
        return domainEvents;
    }
}
