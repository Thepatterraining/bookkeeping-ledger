package com.zt.bookkeeping.ledger.application.ledger.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.bookkeeping.ledger.application.ledger.dto.CreateLedgerRequest;
import com.zt.bookkeeping.ledger.application.ledger.dto.LedgerListRes;
import com.zt.bookkeeping.ledger.application.ledger.dto.QueryLedgerListRequest;
import com.zt.bookkeeping.ledger.application.ledger.dto.UpdateLedgerRequest;
import com.zt.bookkeeping.ledger.common.res.PageRes;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import com.zt.bookkeeping.ledger.domain.ledger.event.LedgerCreatedEvent;
import com.zt.bookkeeping.ledger.domain.ledger.event.LedgerUpdatedEvent;
import com.zt.bookkeeping.ledger.domain.ledger.service.LedgerDomainService;
import com.zt.bookkeeping.ledger.infrastructure.db.LedgerMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.LedgerPO;
import com.zt.bookkeeping.ledger.infrastructure.util.LocalDateTimeUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LedgerQueryApplicationService {

    @Resource
    private LedgerMapper ledgerMapper;

    @Resource
    private LedgerDomainService ledgerDomainService;

    private List<LedgerListRes> convertToLedgerListResList(List<LedgerPO> po) {
        if (CollectionUtils.isEmpty(po)) {
            return Collections.emptyList();
        }
        return po.stream().map(this::convertToLedgerListRes).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private LedgerListRes convertToLedgerListRes(LedgerPO po) {
        if (po == null) {
            return null;
        }
        LedgerListRes res = new LedgerListRes();
        res.setLedgerName(po.getLedgerName());
        res.setLedgerImage(po.getLedgerImage());
        res.setLedgerNo(po.getLedgerNo());
        res.setLedgerDesc(po.getLedgerDesc());
        res.setLedgerStatus(po.getLedgerStatus());
        res.setCreateTime(LocalDateTimeUtil.format(po.getCreateTime()));
        res.setUpdateTime(LocalDateTimeUtil.format(po.getUpdateTime()));
        return res;
    }

    public PageRes<LedgerListRes> getLedgerList(QueryLedgerListRequest request) {
        // 查询用户账本列表
        LambdaQueryWrapper<LedgerPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LedgerPO::getOwnerNo, "");
        Page<LedgerPO> pageList = ledgerMapper.selectPage(Page.of(request.getPage(), request.getSize()), wrapper);

        // 组装返回
        PageRes<LedgerListRes> pageRes = new PageRes<>();
        pageRes.setPageNum(pageList.getCurrent());
        pageRes.setPageSize(pageList.getSize());
        pageRes.setTotal(pageList.getTotal());
        pageRes.setList(convertToLedgerListResList(pageList.getRecords()));
        return pageRes;
    }
}
