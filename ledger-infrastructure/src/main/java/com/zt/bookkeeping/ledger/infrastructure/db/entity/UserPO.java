package com.zt.bookkeeping.ledger.infrastructure.db.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UserPO {
    private Long id;
    private String userNo;
    private String username;
    private String password;
    private String email;
    private String mobile;
    private Integer gender;
    private Integer age;
    private Integer userStatus;
    private Integer userType;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
