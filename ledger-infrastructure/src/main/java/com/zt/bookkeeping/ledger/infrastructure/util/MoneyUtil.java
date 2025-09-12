package com.zt.bookkeeping.ledger.infrastructure.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Desc:
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/9/3
 * Time:17:12
 */
public class MoneyUtil {
    public static String fen2Yuan(Integer fen) {
        if (fen == null) {
            return null;
        }
        return BigDecimal.valueOf(fen).divide(new BigDecimal(100),2, RoundingMode.HALF_EVEN).toString();
    }

    public static BigDecimal fen2Yuan(Long fen) {
        if (fen == null) {
            return null;
        }
        return new BigDecimal(fen).divide(new BigDecimal(100),2, RoundingMode.HALF_EVEN);
    }
}
