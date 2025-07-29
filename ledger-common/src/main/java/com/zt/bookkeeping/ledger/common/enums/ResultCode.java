package com.zt.bookkeeping.ledger.common.enums;

/**
 * 响应状态码枚举
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/7/9
 * Time:17:35
 */
public enum ResultCode {

    // 成功
    SUCCESS(200, "操作成功"),

    // 客户端错误
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),

    // 服务器错误
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    BAD_GATEWAY(502, "网关错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),

    // 业务错误
    BUSINESS_ERROR(1000, "业务处理失败"),
    VALIDATION_ERROR(1001, "数据校验失败"),
    LOGIN_ERROR(1002, "登录失败"),
    PERMISSION_DENIED(1003, "权限不足"),
    DATA_NOT_FOUND(1004, "数据不存在"),
    DATA_ALREADY_EXISTS(1005, "数据已存在"),
    OPERATION_FAILED(1006, "操作失败"),

    LEDGER_NOT_FOUND(1007, "账本不存在"),
    LEDGER_ALREADY_EXISTS(1008, "账本已存在"),

    ;

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

