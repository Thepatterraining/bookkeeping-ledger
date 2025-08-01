package com.zt.bookkeeping.ledger.infrastructure.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Desc:
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/7/29
 * Time:17:48
 */
@Component
@Order(1)
public class UserIdFilter implements Filter {

    private static final String USER_ID_HEADER = "X-userID";
    private static final String USER_ID_ATTRIBUTE = "userNo";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String userId = httpRequest.getHeader(USER_ID_HEADER);

        if (userId != null) {
            request.setAttribute(USER_ID_ATTRIBUTE, userId);
        }

        chain.doFilter(request, response);
    }
}

