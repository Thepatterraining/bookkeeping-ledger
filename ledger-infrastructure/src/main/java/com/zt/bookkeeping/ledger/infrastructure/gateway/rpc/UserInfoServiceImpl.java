package com.zt.bookkeeping.ledger.infrastructure.gateway.rpc;

import com.zt.bookkeeping.user.api.request.BatchQueryUserInfoRequest;
import com.zt.bookkeeping.user.api.response.BatchQueryUserInfoResponse;
import com.zt.bookkeeping.user.api.response.UserDTO;
import com.zt.bookkeeping.user.api.service.IUserService;
import com.zt.bookkeeping.ledger.domain.ledger.bo.UserInfoBO;
import com.zt.bookkeeping.ledger.domain.ledger.gateway.rpc.UserInfoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Desc:
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/9/11
 * Time:17:29
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @DubboReference()
    private IUserService userService;

    public List<UserInfoBO> batchQueryUserInfo(List<String> userNoList) {
        // 构建请求信息
        BatchQueryUserInfoRequest req = buildBatchQueryUserRequest(userNoList);
        // 发送请求
        BatchQueryUserInfoResponse res = userService.batchQueryUserInfo(req);

        // 构建返回信息
        return res.getUserDTOList().stream().map(this::buildUserBO).collect(Collectors.toList());
    }

    private UserInfoBO buildUserBO(UserDTO userDTO) {
        UserInfoBO userBO = new UserInfoBO();
        userBO.setUserNo(userDTO.getUserNo());
        userBO.setUserName(userDTO.getUserName());
        userBO.setUserAvatar(userDTO.getUserAvatar());
        return userBO;
    }

    private BatchQueryUserInfoRequest buildBatchQueryUserRequest(List<String> userNoList) {
        BatchQueryUserInfoRequest  request = new BatchQueryUserInfoRequest();
        request.setUserNoList(userNoList);
        return request;
    }

}
