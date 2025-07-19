package com.zt.bookkeeping.ledger.domain.user.service;

import com.zt.bookkeeping.ledger.common.enums.ResultCode;
import com.zt.bookkeeping.ledger.domain.exception.DomainException;
import com.zt.bookkeeping.ledger.domain.exception.UserLoginException;
import com.zt.bookkeeping.ledger.domain.user.entity.UserAgg;
import com.zt.bookkeeping.ledger.domain.user.respository.UserRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserAggService {

    @Resource
    private UserRepository userRepository;

    public UserAgg getUserByUserName(String userName) {
        return userRepository.getUser(userName);
    }

    public void canLogin(UserAgg userAgg, String inputPassword) throws UserLoginException {
        // 0. 校验用户存在
        if (userAgg == null) {
            log.warn("用户不存在");
            throw new UserLoginException(ResultCode.USER_NOT_FOUND);
        }

        // 1. 校验用户密码
        if (!userAgg.validatePassword(inputPassword)) {
            log.warn("用户密码错误,用户输入的密码:{}, 用户的密码:{}",  inputPassword, userAgg.getPassword());
            throw new UserLoginException(ResultCode.USER_PASSWORD_ERROR);
        }

        // 2. 校验用户状态
        if (!userAgg.validateStatus()) {
            log.warn("用户状态错误:{}", userAgg.getUserStatus());
            throw new UserLoginException(ResultCode.USER_STATUS_ERROR);
        }
    }

    public void canRegister(String mobile) {
        // 根据要注册的手机号获取用户信息
        UserAgg userAgg = userRepository.getUserByMobile(mobile);
        // 校验用户是否存在
        if (userAgg != null) {
            log.warn("用户已存在");
            throw new DomainException(ResultCode.USER_ALREADY_EXISTS);
        }
    }

    public void save(UserAgg userAgg) {
        userRepository.insert(userAgg);
    }

}
