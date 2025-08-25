package com.zt.bookkeeping.ledger.application.ledger.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zt.bookkeeping.ledger.application.ledger.dto.CategoryListRes;
import com.zt.bookkeeping.ledger.application.ledger.dto.LedgerListRes;
import com.zt.bookkeeping.ledger.application.ledger.dto.QueryLedgerListRequest;
import com.zt.bookkeeping.ledger.common.res.PageRes;
import com.zt.bookkeeping.ledger.domain.ledger.service.LedgerDomainService;
import com.zt.bookkeeping.ledger.domain.sysCategory.entity.SysCategoryAgg;
import com.zt.bookkeeping.ledger.domain.sysCategory.repository.SysCategoryRepository;
import com.zt.bookkeeping.ledger.domain.userCategory.entity.UserCategoryAgg;
import com.zt.bookkeeping.ledger.domain.userCategory.repository.UserCategoryRepository;
import com.zt.bookkeeping.ledger.domain.userCategory.service.UserCategoryDomainService;
import com.zt.bookkeeping.ledger.infrastructure.db.LedgerMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.LedgerPO;
import com.zt.bookkeeping.ledger.infrastructure.util.LocalDateTimeUtil;
import com.zt.bookkeeping.ledger.infrastructure.util.UserContextHolder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class CategoryQueryApplicationService {

    @Resource
    private UserCategoryRepository userCategoryRepository;

    @Resource
    private SysCategoryRepository sysCategoryRepository;

    private List<CategoryListRes> convertToLedgerListResList(List<SysCategoryAgg> sysCategoryAggList, List<UserCategoryAgg> userCategoryAggList) {
        if (CollectionUtils.isEmpty(sysCategoryAggList) && CollectionUtils.isEmpty(userCategoryAggList)) {
            return Collections.emptyList();
        }
        List<CategoryListRes> sysCategoryList = sysCategoryAggList.stream()
                .map(this::convertToLedgerListRes).filter(Objects::nonNull).collect(Collectors.toList());
        List<CategoryListRes> userCategoryList = userCategoryAggList.stream().map(this::convertToLedgerListRes).filter(Objects::nonNull).collect(Collectors.toList());
        return Stream.concat(sysCategoryList.stream(), userCategoryList.stream()).collect(Collectors.toList());
    }

    private CategoryListRes convertToLedgerListRes(SysCategoryAgg agg) {
        if (agg == null) {
            return null;
        }
        CategoryListRes res = new CategoryListRes();
        res.setCategoryName(agg.getCategoryName());
        res.setCategoryNo(agg.getCategoryNo());
        res.setCategoryDesc(agg.getCategoryDesc());
        res.setCategoryLevel(agg.getCategoryLevel());
        res.setCategoryIcon(agg.getCategoryIcon());
        return res;
    }

    private CategoryListRes convertToLedgerListRes(UserCategoryAgg agg) {
        if (agg == null) {
            return null;
        }
        CategoryListRes res = new CategoryListRes();
        res.setCategoryName(agg.getCategoryName());
        res.setCategoryNo(agg.getCategoryNo());
        res.setCategoryDesc(agg.getCategoryDesc());
        res.setCategoryLevel(agg.getCategoryLevel());
        res.setCategoryIcon(agg.getCategoryIcon());
        return res;
    }

    public PageRes<CategoryListRes> getAllUserCategories(QueryLedgerListRequest request) {
        // 获取用户ID
        String userNo = UserContextHolder.getCurrentUserNo();
        // 查询所有系统分类
        List<SysCategoryAgg> sysCategoryAggList = sysCategoryRepository.loadAll();
        // 查询所有用户分类
        List<UserCategoryAgg> userCategoryAggList = userCategoryRepository.loadListByUserNo(userNo);
        // 组装返回
        PageRes<CategoryListRes> pageRes = new PageRes<>();
        pageRes.setPageNum(1L);
        pageRes.setPageSize((long) (sysCategoryAggList.size() + userCategoryAggList.size()));
        pageRes.setTotal((long) (sysCategoryAggList.size() + userCategoryAggList.size()));
        pageRes.setList(convertToLedgerListResList(sysCategoryAggList, userCategoryAggList));
        return pageRes;
    }
}
