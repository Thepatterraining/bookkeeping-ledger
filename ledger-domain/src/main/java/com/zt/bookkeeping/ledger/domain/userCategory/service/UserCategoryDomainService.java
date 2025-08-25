package com.zt.bookkeeping.ledger.domain.userCategory.service;

import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.TransactionStatementAgg;
import com.zt.bookkeeping.ledger.domain.transactionStatement.repository.TransactionStatementRepository;
import com.zt.bookkeeping.ledger.domain.userCategory.entity.UserCategoryAgg;
import com.zt.bookkeeping.ledger.domain.userCategory.repository.UserCategoryRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Desc:
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/8/20
 * Time:10:53
 */
@Service
public class UserCategoryDomainService {

    @Resource
    private UserCategoryRepository userCategoryRepository;

    public void save(UserCategoryAgg userCategoryAgg) {
        if (userCategoryAgg.getId() == null) {
            userCategoryRepository.insert(userCategoryAgg);
        } else {
            userCategoryRepository.update(userCategoryAgg);
        }
    }

    /**
     * 检查分类名称是否重复
     */
    public void checkCategoryNameDuplicate(String categoryName, String userNo) {
        UserCategoryAgg existingCategories = userCategoryRepository.findByNameAndUserNo(categoryName, userNo);

        if (Objects.nonNull(existingCategories)) {
            throw new IllegalStateException("分类名称已存在");
        }
    }

    /**
     * 检查父分类是否存在
     */
    public void validateParentCategory(String parentCategoryNo, String userNo) {
        if (parentCategoryNo == null) {
            return; // 根分类不需要检查父分类
        }

        UserCategoryAgg parentCategory = userCategoryRepository.load(parentCategoryNo);
        if (parentCategory == null) {
            throw new IllegalArgumentException("父分类不存在");
        }

        // 如果是用户分类，需要检查父分类是否属于同一用户或系统分类
        if (userNo != null && !userNo.equals(parentCategory.getUserNo())) {
            throw new IllegalArgumentException("不能选择其他用户的分类作为父分类");
        }
    }
}
