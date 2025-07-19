package com.zt.bookkeeping.ledger.domain.user.event;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserRegisteredEvent {
    private final Long userId;
    private final String username;
    private final String ip;
    private final LocalDateTime registerTime;

    public UserRegisteredEvent(Long userId, String username, String ip, LocalDateTime registerTime) {
        this.userId = userId;
        this.username = username;
        this.ip = ip;
        this.registerTime = registerTime;
    }

    @Override
    public String toString() {
        return "{ userId: " + userId + ", username: " + username + ", ip: " + ip + ", registerTime: " + registerTime + "}";
    }
}
