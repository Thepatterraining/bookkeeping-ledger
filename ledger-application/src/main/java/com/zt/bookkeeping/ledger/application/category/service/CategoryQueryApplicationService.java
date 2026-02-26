package com.zt.bookkeeping.ledger.application.category.service;

import com.zt.bookkeeping.ledger.application.category.dto.CategoryListRes;
import com.zt.bookkeeping.ledger.application.ledger.dto.QueryCategoryListRequest;
import com.zt.bookkeeping.ledger.application.ledger.dto.QueryLedgerListRequest;
import com.zt.bookkeeping.ledger.common.enums.CategoryTypeEnum;
import com.zt.bookkeeping.ledger.common.res.PageRes;
import com.zt.bookkeeping.ledger.domain.sysCategory.entity.SysCategoryAgg;
import com.zt.bookkeeping.ledger.domain.sysCategory.repository.SysCategoryRepository;
import com.zt.bookkeeping.ledger.domain.userCategory.entity.UserCategoryAgg;
import com.zt.bookkeeping.ledger.domain.userCategory.repository.UserCategoryRepository;
import com.zt.bookkeeping.ledger.infrastructure.util.UserContextHolder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class CategoryQueryApplicationService {

    @Resource
    private UserCategoryRepository userCategoryRepository;

    @Resource
    private SysCategoryRepository sysCategoryRepository;

    private List<CategoryListRes> convertToCategoryListResList(List<SysCategoryAgg> sysCategoryAggList, List<UserCategoryAgg> userCategoryAggList) {
        if (CollectionUtils.isEmpty(sysCategoryAggList) && CollectionUtils.isEmpty(userCategoryAggList)) {
            return Collections.emptyList();
        }
        List<CategoryListRes> sysCategoryList = sysCategoryAggList.stream()
                .map(this::convertToCategoryListRes).filter(Objects::nonNull).toList();
        List<CategoryListRes> userCategoryList = userCategoryAggList.stream().map(this::convertToCategoryListRes).filter(Objects::nonNull).toList();
        return Stream.concat(sysCategoryList.stream(), userCategoryList.stream()).collect(Collectors.toList());
    }

    private CategoryListRes convertToCategoryListRes(SysCategoryAgg agg) {
        if (agg == null) {
            return null;
        }
        CategoryListRes res = new CategoryListRes();
        res.setCategoryName(agg.getCategoryName());
        res.setCategoryNo(agg.getCategoryNo());
        res.setCategoryDesc(agg.getCategoryDesc());
        res.setCategoryLevel(agg.getCategoryLevel());
        res.setCategoryType(agg.getCategoryType());
        res.setType(CategoryTypeEnum.SYS.getCode());
        res.setCategoryIcon(agg.getCategoryIcon());
        res.setSubCategoryList(agg.getSubCategories().stream().map(this::convertToCategoryListRes).collect(Collectors.toList()));
        return res;
    }

    private CategoryListRes convertToCategoryListRes(UserCategoryAgg agg) {
        if (agg == null) {
            return null;
        }
        CategoryListRes res = new CategoryListRes();
        res.setCategoryName(agg.getCategoryName());
        res.setCategoryNo(agg.getCategoryNo());
        res.setCategoryDesc(agg.getCategoryDesc());
        res.setCategoryLevel(agg.getCategoryLevel());
        res.setCategoryType(agg.getCategoryType());
        res.setType(CategoryTypeEnum.USER.getCode());
        res.setCategoryIcon(agg.getCategoryIcon());
        res.setSubCategoryList(agg.getSubCategories().stream().map(this::convertToCategoryListRes).collect(Collectors.toList()));
        return res;
    }

    private List<SysCategoryAgg> getAllSysCategoryAgg(Integer categoryType) {
        // 查询所有系统分类
        List<SysCategoryAgg> sysCategoryAggList = sysCategoryRepository.loadAll(categoryType);
        Map<String, SysCategoryAgg> categoryMap = new HashMap<>();
        List<SysCategoryAgg> rootCategories = new ArrayList<>();

        // 放入map
        for (SysCategoryAgg category : sysCategoryAggList) {
            categoryMap.put(category.getCategoryNo(), category);
        }
        // 将根分类放入根分类中
        sysCategoryAggList.stream().filter(SysCategoryAgg::isParent).forEach(rootCategories::add);

        // 将二级分类放入根分类中
        sysCategoryAggList.stream().filter(category -> !category.isParent()).forEach(agg -> {
            SysCategoryAgg parent = categoryMap.get(agg.getParentNo());
            if (parent != null) {
                parent.addSubCategory(agg);
            }
        });

        return rootCategories;
    }

    private List<UserCategoryAgg> getUserCategoryAggList(Integer categoryType) {
        // 获取用户ID
        String userNo = UserContextHolder.getCurrentUserNo();
        // 查询所有用户分类
        List<UserCategoryAgg> userCategoryAggList = userCategoryRepository.loadListByUserNo(userNo, categoryType);
        Map<String, UserCategoryAgg> categoryMap = new HashMap<>();
        List<UserCategoryAgg> rootCategories = new ArrayList<>();

        // 将所有分类放入映射
        for (UserCategoryAgg category : userCategoryAggList) {
            categoryMap.put(category.getCategoryNo(), category);
        }

        // 将根分类放入根分类中
        userCategoryAggList.stream().filter(UserCategoryAgg::isParent).forEach(rootCategories::add);

        // 将二级分类放入根分类中
        userCategoryAggList.stream().filter(category -> !category.isParent()).forEach(agg -> {
            UserCategoryAgg parent = categoryMap.get(agg.getParentNo());
            if (parent != null) {
                parent.addSubCategory(agg);
            }
        });
        return rootCategories;
    }

    public PageRes<CategoryListRes> getAllUserCategories(QueryCategoryListRequest request) {

        // 查询所有系统分类
        List<SysCategoryAgg> sysCategoryAggList = getAllSysCategoryAgg(request.getCategoryType());
        // 查询所有用户分类
        List<UserCategoryAgg> userCategoryAggList = getUserCategoryAggList(request.getCategoryType());
        // 组装返回
        PageRes<CategoryListRes> pageRes = new PageRes<>();
        pageRes.setPageNum(1L);
        pageRes.setPageSize((long) (sysCategoryAggList.size() + userCategoryAggList.size()));
        pageRes.setTotal((long) (sysCategoryAggList.size() + userCategoryAggList.size()));
        pageRes.setList(convertToCategoryListResList(sysCategoryAggList, userCategoryAggList));
        return pageRes;
    }

    public PageRes<CategoryListRes> getUserCategories(QueryCategoryListRequest request) {
        // 查询所有用户分类
        List<UserCategoryAgg> userCategoryAggList = getUserCategoryAggList(request.getCategoryType());
        // 组装返回
        PageRes<CategoryListRes> pageRes = new PageRes<>();
        pageRes.setPageNum(1L);
        pageRes.setPageSize((long) (userCategoryAggList.size()));
        pageRes.setTotal((long) (userCategoryAggList.size()));
        pageRes.setList(convertToCategoryListResList(new ArrayList<>(), userCategoryAggList));
        return pageRes;
    }
}
