package com.zt.bookkeeping.ledger.domain.user.event;

import java.time.LocalDateTime;

public class UserLoggedInEvent {
    private final Long userId;
    private final String username;
    private final String ip;
    private final LocalDateTime loginTime;

    public UserLoggedInEvent(Long userId, String username, String ip, LocalDateTime loginTime) {
        this.userId = userId;
        this.username = username;
        this.ip = ip;
        this.loginTime = loginTime;
    }

    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getIp() { return ip; }
    public LocalDateTime getLoginTime() { return loginTime; }

    @Override
    public String toString() {
        return "{ userId: " + userId + ", username: " + username + ", ip: " + ip + ", loginTime: " + loginTime + "}";
    }
}
