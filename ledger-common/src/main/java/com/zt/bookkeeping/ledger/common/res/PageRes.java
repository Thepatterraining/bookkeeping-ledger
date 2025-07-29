package com.zt.bookkeeping.ledger.common.res;

import lombok.Data;

import java.util.List;

/**
 * Desc:
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/7/29
 * Time:16:38
 */
@Data
public class PageRes<E> {
    private Long total;
    private Long pageSize;
    private Long pageNum;
    private List<E> list;
}
