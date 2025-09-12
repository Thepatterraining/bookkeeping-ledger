package com.zt.bookkeeping.ledger.application.category.service;

import com.zt.bookkeeping.ledger.application.category.dto.CreateUserCategoryRequest;
import com.zt.bookkeeping.ledger.common.base.DomainEvent;
import com.zt.bookkeeping.ledger.domain.userCategory.entity.UserCategoryAgg;
import com.zt.bookkeeping.ledger.domain.userCategory.factory.UserCategoryFactory;
import com.zt.bookkeeping.ledger.domain.userCategory.service.UserCategoryDomainService;
import com.zt.bookkeeping.ledger.infrastructure.util.UserContextHolder;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Desc:
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/8/20
 * Time:14:39
 */
@Service
public class CategoryCommandApplicationService {

    @Resource
    private ApplicationEventPublisher eventPublisher;

    @Resource
    private UserCategoryDomainService userCategoryDomainService;

    @Resource
    private UserCategoryFactory userCategoryFactory;

    private static final int MAX_CATEGORY_DEPTH = 2; // 最大分类层级

    public String createCategory(CreateUserCategoryRequest request) {
        // 获取用户ID
        String userNo = UserContextHolder.getCurrentUserNo();

        // 查询分类名称是否存在
        userCategoryDomainService.checkCategoryNameDuplicate(request.getCategoryName(), userNo);

        // 验证父分类
        userCategoryDomainService.validateParentCategory(request.getParentNo(), userNo);

        // 验证分类层级
//        userCategoryDomainService.validateCategoryDepth(request.getParentNo(), MAX_CATEGORY_DEPTH);

        // 分类不存在则创建分类
        // 创建用户分类
        UserCategoryAgg categoryAgg = userCategoryFactory.createUserCategory(
                request.getCategoryName(),
                userNo,
                request.getParentNo(),
                request.getTransactionDesc(),
                request.getCategoryIcon(),
                request.getCategoryLevel()
        );
        categoryAgg.create();

        // 保存分类
        userCategoryDomainService.save(categoryAgg);

        // 获取注册的事件进行发布
        List<DomainEvent> domainEventList = categoryAgg.getDomainEvents();
        domainEventList.forEach(event -> eventPublisher.publishEvent(event));

        // 返回编号
        return categoryAgg.getCategoryNo();
    }
}
