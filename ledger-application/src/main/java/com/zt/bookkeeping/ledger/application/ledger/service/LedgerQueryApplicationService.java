package com.zt.bookkeeping.ledger.application.ledger.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.bookkeeping.ledger.application.ledger.dto.*;
import com.zt.bookkeeping.ledger.common.api.UserService;
import com.zt.bookkeeping.ledger.common.dto.UserDTO;
import com.zt.bookkeeping.ledger.common.res.PageRes;
import com.zt.bookkeeping.ledger.domain.ledger.bo.UserInfoBO;
import com.zt.bookkeeping.ledger.domain.ledger.entity.*;
import com.zt.bookkeeping.ledger.domain.ledger.event.LedgerCreatedEvent;
import com.zt.bookkeeping.ledger.domain.ledger.event.LedgerUpdatedEvent;
import com.zt.bookkeeping.ledger.domain.ledger.gateway.rpc.UserInfoService;
import com.zt.bookkeeping.ledger.domain.ledger.service.LedgerDomainService;
import com.zt.bookkeeping.ledger.infrastructure.db.LedgerMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.LedgerMemberMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.LedgerMemberPO;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.LedgerPO;
import com.zt.bookkeeping.ledger.infrastructure.util.LocalDateTimeUtil;
import com.zt.bookkeeping.ledger.infrastructure.util.MoneyUtil;
import com.zt.bookkeeping.ledger.infrastructure.util.UserContextHolder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LedgerQueryApplicationService {

    @Resource
    private LedgerMapper ledgerMapper;

    @Resource
    private LedgerMemberMapper ledgerMemberMapper;

    @Resource
    private LedgerDomainService ledgerDomainService;

    @Resource
    private UserInfoService userInfoService;

    private List<LedgerListRes> convertToLedgerListResList(List<LedgerMemberPO> memberList, Map<String, LedgerPO> ledgerMap) {
        if (CollectionUtils.isEmpty(memberList)) {
            return Collections.emptyList();
        }
        return memberList.stream().map(member -> {return convertToLedgerListRes(member, ledgerMap.get(member.getLedgerNo()));}).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private LedgerListRes convertToLedgerListRes(LedgerMemberPO memberPO, LedgerPO ledgerPO) {
        if (memberPO == null || ledgerPO == null) {
            return null;
        }
        LedgerListRes res = new LedgerListRes();
        res.setLedgerName(ledgerPO.getLedgerName());
        res.setLedgerImage(ledgerPO.getLedgerImage());
        res.setLedgerNo(ledgerPO.getLedgerNo());
        res.setLedgerDesc(ledgerPO.getLedgerDesc());
        res.setLedgerStatus(ledgerPO.getLedgerStatus());
        res.setCreateTime(LocalDateTimeUtil.format(ledgerPO.getCreateTime()));
        res.setUpdateTime(LocalDateTimeUtil.format(ledgerPO.getUpdateTime()));
        res.setRole(LedgerMemberRoleVO.of(memberPO.getRole()).getLabel());
        res.setJoinTime(LocalDateTimeUtil.format(memberPO.getJoinTime()));
        return res;
    }

    public LedgerDetailRes getLedger(QueryLedgerRequest request) {
        // 获取用户ID
        String userNo = UserContextHolder.getCurrentUserNo();
        // 加载账本聚合
        LedgerAgg ledgerAgg = ledgerDomainService.findByNo(request.getLedgerNo());
        if (ledgerAgg == null) {
            return null;
        }
        // 判断这个用户是否有查看权限
        if (!ledgerAgg.hasViewPermission(userNo)) {
            return null;
        }
        // 有查看权限则返回数据
        return convertToLedgerRes(ledgerAgg);
    }

    private LedgerDetailRes convertToLedgerRes(LedgerAgg ledgerAgg) {
        LedgerDetailRes res = new LedgerDetailRes();
        res.setLedgerNo(ledgerAgg.getLedgerNo());
        res.setLedgerName(ledgerAgg.getLedgerName());
        res.setLedgerDesc(ledgerAgg.getLedgerDesc());
        res.setLedgerStatus(ledgerAgg.getLedgerStatus().getLabel());
        res.setLedgerBudget(buildLedgerBudget(ledgerAgg.getLastLedgerBudget()));
        res.setLedgerSummary(buildLedgerSummary(ledgerAgg.getLastLedgerSummary()));
        return res;
    }

    private LedgerSummaryRes buildLedgerSummary(LedgerSummaryVO summaryVO) {
        LedgerSummaryRes res = new LedgerSummaryRes();
        res.setIncome(summaryVO.getIncome());
        res.setExpense(summaryVO.getExpense());
        res.setRemained(summaryVO.getRemained());
        return res;
    }

    private LedgerBudgetRes buildLedgerBudget(LedgerBudgetVO budgetPO) {
        LedgerBudgetRes res = new LedgerBudgetRes();
        res.setAmount(MoneyUtil.fen2Yuan(budgetPO.getBudgetAmount()));
        res.setUsed(MoneyUtil.fen2Yuan(budgetPO.getUsedAmount()));
        res.setRemained(MoneyUtil.fen2Yuan(budgetPO.getRemainedAmount()));
        res.setBudgetDate(LocalDateTimeUtil.format(budgetPO.getBudgetDate()));
        return res;
    }


    public PageRes<LedgerListRes> getLedgerList(QueryLedgerListRequest request) {
        // 获取用户ID
        String userNo = UserContextHolder.getCurrentUserNo();
        // 查询用户有哪些账本
        LambdaQueryWrapper<LedgerMemberPO> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(LedgerMemberPO::getUserNo, userNo)
                .eq(LedgerMemberPO::getStatus, LedgerMemberStatusVO.NORMAL.getCode())
                .eq(LedgerMemberPO::getIsDeleted, false)
                .orderByDesc(LedgerMemberPO::getJoinTime);
        Page<LedgerMemberPO> pageList = ledgerMemberMapper.selectPage(Page.of(request.getPage(), request.getSize()),
                wrapper1);
        if (pageList.getTotal() == 0) {
            // 组装返回
            PageRes<LedgerListRes> pageRes = new PageRes<>();
            pageRes.setPageNum(pageList.getCurrent());
            pageRes.setPageSize(pageList.getSize());
            pageRes.setTotal(pageList.getTotal());
            pageRes.setList(Collections.emptyList());
            return pageRes;
        }
        // 查询用户账本信息
        List<String> ledgerNoList = pageList.getRecords().stream()
                .map(LedgerMemberPO::getLedgerNo).collect(Collectors.toList());
        LambdaQueryWrapper<LedgerPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(LedgerPO::getLedgerNo, ledgerNoList)
                .eq(LedgerPO::getIsDeleted, false);
        List<LedgerPO> ledgerList = ledgerMapper.selectList(wrapper);
        Map<String, LedgerPO> ledgerMap = ledgerList.stream()
                .collect(Collectors.toMap(LedgerPO::getLedgerNo, Function.identity()));

        // 组装返回
        PageRes<LedgerListRes> pageRes = new PageRes<>();
        pageRes.setPageNum(pageList.getCurrent());
        pageRes.setPageSize(pageList.getSize());
        pageRes.setTotal(pageList.getTotal());
        pageRes.setList(convertToLedgerListResList(pageList.getRecords(), ledgerMap));
        return pageRes;
    }

    public List<LedgerMemberListRes> getMemberList(QueryLedgerMemberListRequest request) {
        // 获取成员列表
        LambdaQueryWrapper<LedgerMemberPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LedgerMemberPO::getLedgerNo, request.getLedgerNo())
                .eq(LedgerMemberPO::getStatus, LedgerMemberStatusVO.NORMAL.getCode())
                .eq(LedgerMemberPO::getIsDeleted, false);
        List<LedgerMemberPO> memberList = ledgerMemberMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(memberList)) {
            return Collections.emptyList();
        }
        // 判断权限
        String userNo = UserContextHolder.getCurrentUserNo();
        if (memberList.stream().noneMatch(member -> member.getUserNo().equals(userNo))) {
            // 没有权限
            return Collections.emptyList();
        }

        // 使用dubbo查询用户服务的用户信息
        List<String> userNoList = memberList.stream()
                .map(LedgerMemberPO::getUserNo)
                .collect(Collectors.toList());
        List<UserInfoBO> userBOList = userInfoService.batchQueryUserInfo(userNoList);
        Map<String, UserInfoBO> userBOMap = userBOList.stream()
                .collect(Collectors.toMap(UserInfoBO::getUserNo, Function.identity()));

        // 转换返回
        return memberList.stream()
                .map(member -> convertToMemberListRes(member, userBOMap.get(member.getUserNo())))
                .collect(Collectors.toList());
    }

    private LedgerMemberListRes convertToMemberListRes(LedgerMemberPO memberPO, UserInfoBO userBO) {
        LedgerMemberListRes res = new LedgerMemberListRes();
        res.setUserNo(memberPO.getUserNo());
        res.setRole(LedgerMemberRoleVO.of(memberPO.getRole()).getLabel());
        res.setJoinTime(LocalDateTimeUtil.format(memberPO.getJoinTime()));

        if (userBO != null) {
            res.setUsername(userBO.getUserName());
            res.setAvatar(userBO.getUserAvatar());
        }

        return res;
    }
}
