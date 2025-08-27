package com.zt.bookkeeping.ledger.infrastructure.factory;

import com.zt.bookkeeping.ledger.domain.generator.SnowFlakeGenerator;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerBudgetVO;
import com.zt.bookkeeping.ledger.domain.ledger.factory.LedgerFactory;
import com.zt.bookkeeping.ledger.domain.userCategory.entity.UserCategoryAgg;
import com.zt.bookkeeping.ledger.domain.userCategory.factory.UserCategoryFactory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class UserCategoryFactoryImpl implements UserCategoryFactory {

    @Resource
    private SnowFlakeGenerator snowFlakeGenerator;

    @Override
    public UserCategoryAgg createUserCategory(String categoryName, String userNo, String parentCategoryNo,
            String description, String categoryIcon, Integer categoryLevel) {
        validateCategoryName(categoryName);
        validateUserId(userNo);
        String no = snowFlakeGenerator.nextId("user_category");
        return UserCategoryAgg.builder()
                .categoryName(categoryName)
                .categoryNo(no)
                .categoryIcon(categoryIcon)
                .categoryLevel(categoryLevel)
                .categoryDesc(description)
                .parentNo(parentCategoryNo)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
    }

    private static void validateCategoryName(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("分类名称不能为空");
        }
        if (categoryName.length() > 10) {
            throw new IllegalArgumentException("分类名称长度不能超过10个字符");
        }
    }

    private static void validateUserId(String userNo) {
        if (userNo == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
    }
}
