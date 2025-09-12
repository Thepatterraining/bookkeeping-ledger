package com.zt.bookkeeping.ledger.common.api;

import com.zt.bookkeeping.ledger.common.dto.UserDTO;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 根据用户编号获取用户信息
     *
     * @param userNo 用户编号
     * @return 用户信息
     */
    UserDTO getUserByUserNo(String userNo);

    /**
     * 批量获取用户信息
     *
     * @param userNoList 用户编号列表
     * @return 用户信息列表
     */
    List<UserDTO> batchGetUserByUserNos(List<String> userNoList);
}

