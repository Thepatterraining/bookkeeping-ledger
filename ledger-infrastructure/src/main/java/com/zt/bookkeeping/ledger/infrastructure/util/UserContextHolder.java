package com.zt.bookkeeping.ledger.infrastructure.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Desc:
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/7/29
 * Time:18:00
 */
public class UserContextHolder {

    private static final String USER_ID_ATTRIBUTE = "userNo";

    public static String getCurrentUserNo() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            String userNo = (String) request.getAttribute(USER_ID_ATTRIBUTE);
            if (userNo == null) {
                throw new RuntimeException("用户未登录");
            }
            return userNo;
        }
        return null;
    }
}
