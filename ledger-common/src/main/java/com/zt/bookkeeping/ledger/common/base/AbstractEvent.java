package com.zt.bookkeeping.ledger.common.base;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Desc:
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/7/28
 * Time:17:58
 */
@Getter
public class AbstractEvent<T> implements DomainEvent {

    private final LocalDateTime eventTime;
    private final String eventType;
    private final String eventId;
    private final T eventData;

    public AbstractEvent(T eventData, String eventType) {
        this.eventTime = LocalDateTime.now();
        this.eventData = eventData;
        this.eventId = UUID.randomUUID().toString();
        this.eventType = eventType;
    }
}
