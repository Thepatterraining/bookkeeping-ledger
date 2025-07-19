package com.zt.bookkeeping.ledger.domain.jwt;

/**
 * Desc:
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/7/14
 * Time:19:37
 */
public interface JwtTokenService {
    String generateToken(String username);
}
