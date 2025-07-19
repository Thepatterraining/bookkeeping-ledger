package com.zt.bookkeeping.ledger.starter.controller;

import com.zt.bookkeeping.ledger.infrastructure.common.Result;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器 - 展示Result类的使用示例
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/7/9
 * Time:17:45
 */
@RequestMapping("/user")
@RestController
public class UserController {

    /**
     * 获取用户信息 - 成功返回带数据
     */
    @GetMapping("/{id}")
    public Result<Map<String, Object>> getUserById(@PathVariable Long id) {
        if (id <= 0) {
            return Result.error("用户ID不能为空或小于等于0");
        }

        // 模拟查询用户信息
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", id);
        userInfo.put("username", "user_" + id);
        userInfo.put("email", "user" + id + "@example.com");

        return Result.success("查询成功", userInfo);
    }

//    /**
//     * 创建用户 - 使用ResultCode枚举
//     */
//    @PostMapping
//    public Result<Long> createUser(@RequestBody Map<String, String> userInfo) {
//        String username = userInfo.get("username");
//        if (username == null || username.trim().isEmpty()) {
//            return Result.result(ResultCode.VALIDATION_ERROR);
//        }
//
//        // 模拟创建用户
//        Long newUserId = System.currentTimeMillis();
//        return Result.result(ResultCode.SUCCESS, newUserId);
//    }
//
//    /**
//     * 更新用户 - 无返回数据的成功响应
//     */
//    @PutMapping("/{id}")
//    public Result<Void> updateUser(@PathVariable Long id, @RequestBody Map<String, String> userInfo) {
//        if (id <= 0) {
//            return Result.result(ResultCode.BAD_REQUEST);
//        }
//
//        // 模拟更新用户
//        return Result.success("用户信息更新成功", null);
//    }
//
//    /**
//     * 删除用户 - 业务异常处理
//     */
//    @DeleteMapping("/{id}")
//    public Result<Void> deleteUser(@PathVariable Long id) {
//        if (id <= 0) {
//            return Result.result(ResultCode.BAD_REQUEST);
//        }
//
//        // 模拟业务逻辑：管理员用户不能删除
//        if (id == 1L) {
//            return Result.result(ResultCode.PERMISSION_DENIED);
//        }
//
//        // 模拟删除用户
//        return Result.success();
//    }

    /**
     * 获取用户列表 - 自定义状态码和消息
     */
    @GetMapping("/list")
    public Result<Map<String, Object>> getUserList(@RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        if (page <= 0 || size <= 0) {
            return Result.error(400, "分页参数错误");
        }

        // 模拟分页数据
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("page", page);
        pageData.put("size", size);
        pageData.put("total", 100);
        pageData.put("data", "用户列表数据...");

        return Result.success("查询成功", pageData);
    }
}

