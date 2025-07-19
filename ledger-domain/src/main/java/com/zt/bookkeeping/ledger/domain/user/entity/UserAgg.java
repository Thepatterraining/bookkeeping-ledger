package com.zt.bookkeeping.ledger.domain.user.entity;

import com.zt.bookkeeping.ledger.domain.util.EncryptUtil;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Desc:
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/7/10
 * Time:20:27
 */
@Data
@Builder
public class UserAgg {
    private Long id;
    private String userNo;
    private String username;
    private String password;
    private String email;
    private String mobile;
    private Integer gender;
    private Integer age;
    private UserStatus userStatus;
    private UserType userType;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 构造函数
    public UserAgg() {}

    public UserAgg(Long id, String username, String password, String email, String mobile, Integer gender, Integer age, UserStatus userStatus, UserType userType) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender;
        this.age = age;
        this.userStatus = userStatus;
        this.userType = userType;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    public static UserAgg init(String mobile) {
        return UserAgg.builder()
                .userNo("")
                .username(mobile)
                .password("")
                .email("")
                .gender(0)
                .age(0)
                .mobile(mobile)
                .userStatus(UserStatus.ACTIVE)
                .userType(UserType.NORMAL)
                .build();
    }

    // 领域行为方法
    public void changePassword(String newPassword) {
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("密码不能为空");
        }
        this.password = newPassword;
        this.updateTime = LocalDateTime.now();
    }

    public boolean validatePassword(String inputPassword) {
        if (inputPassword == null || inputPassword.trim().isEmpty()) {
            return false;
        }

        // 加密输入的用户密码
        String pwd = EncryptUtil.sha256(inputPassword);

        // 校验
        return this.password.equals(pwd);
    }

    public boolean validateStatus() {
        return userStatus.canLogin();
    }

}
