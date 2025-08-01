package com.zt.bookkeeping.ledger.common.base;

import java.util.ArrayList;
import java.util.List;

public class AbstractAgg {
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public void registerDomainEvent(DomainEvent domainEvent) {
        this.domainEvents.add(domainEvent);
    }

    public List<DomainEvent>  getDomainEvents() {
        return domainEvents;
    }
}
