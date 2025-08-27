package com.zt.bookkeeping.ledger.infrastructure.repository;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerBudgetVO;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerStatusVO;
import com.zt.bookkeeping.ledger.domain.ledger.repository.LedgerRepository;
import com.zt.bookkeeping.ledger.domain.userCategory.entity.UserCategoryAgg;
import com.zt.bookkeeping.ledger.domain.userCategory.repository.UserCategoryRepository;
import com.zt.bookkeeping.ledger.infrastructure.db.LedgerBudgetMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.LedgerMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.UserCategoryMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.LedgerBudgetPO;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.LedgerPO;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.UserCategoryPO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserCategoryRepositoryImpl implements UserCategoryRepository {

    @Resource
    private UserCategoryMapper userCategoryMapper;

    @Resource
    private LedgerBudgetMapper ledgerBudgetMapper;

    @Override
    public void insert(UserCategoryAgg userCategoryAgg) {
        // 插入用户分类信息
        UserCategoryPO userCategoryPO = this.toPO(userCategoryAgg);
        userCategoryMapper.insert(userCategoryPO);
    }

    private UserCategoryPO toPO(UserCategoryAgg userCategoryAgg) {
        return UserCategoryPO.builder()
                .id(userCategoryAgg.getId())
                .categoryNo(userCategoryAgg.getCategoryNo())
                .categoryName(userCategoryAgg.getCategoryName())
                .categoryIcon(userCategoryAgg.getCategoryIcon())
                .categoryLevel(userCategoryAgg.getCategoryLevel())
                .categoryDesc(userCategoryAgg.getCategoryDesc())
                .parentNo(userCategoryAgg.getParentNo())
                .userNo(userCategoryAgg.getUserNo())
                .build();
    }

    private UserCategoryAgg toEntity(UserCategoryPO userCategoryPO) {
        UserCategoryAgg userCategoryAgg = UserCategoryAgg.builder()
                .id(userCategoryPO.getId())
                .categoryNo(userCategoryPO.getCategoryNo())
                .categoryName(userCategoryPO.getCategoryName())
                .categoryIcon(userCategoryPO.getCategoryIcon())
                .categoryLevel(userCategoryPO.getCategoryLevel())
                .categoryDesc(userCategoryPO.getCategoryDesc())
                .parentNo(userCategoryPO.getParentNo())
                .userNo(userCategoryPO.getUserNo())
                .createTime(userCategoryPO.getCreateTime())
                .updateTime(userCategoryPO.getUpdateTime())
                .subCategories(new ArrayList<>())
                .build();
        return userCategoryAgg;
    }

    @Override
    public UserCategoryAgg load(String categoryNo, String userNo) {
        // 查询用户分类基本信息
        LambdaQueryWrapper<UserCategoryPO>  wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCategoryPO::getCategoryNo, categoryNo);
        if (StringUtils.isNotBlank(userNo)) {
            wrapper.eq(UserCategoryPO::getUserNo, userNo);
        }
        UserCategoryPO userCategoryPO = userCategoryMapper.selectOne(wrapper);
        if (userCategoryPO == null) {
            return null;
        }
        return toEntity(userCategoryPO);
    }

    @Override
    public UserCategoryAgg load(String categoryNo) {
        return load(categoryNo, null);
    }

    @Override
    public List<UserCategoryAgg> loadListByUserNo(String userNo) {
        // 查询用户分类基本信息
        LambdaQueryWrapper<UserCategoryPO>  wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCategoryPO::getUserNo, userNo);
        List<UserCategoryPO> userCategoryPOList = userCategoryMapper.selectList(wrapper);
        if (userCategoryPOList == null) {
            return new ArrayList<>();
        }
        return userCategoryPOList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public UserCategoryAgg findByNameAndUserNo(String name, String userNo) {
        LambdaQueryWrapper<UserCategoryPO>  wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCategoryPO::getCategoryName, name);
        wrapper.eq(UserCategoryPO::getUserNo, userNo);
        UserCategoryPO userCategoryPO = userCategoryMapper.selectOne(wrapper);
        if (userCategoryPO == null) {
            return null;
        }
        return toEntity(userCategoryPO);
    }

    @Override
    public void update(UserCategoryAgg userCategoryAgg) {
        // 更新用户分类信息
        UserCategoryPO userCategoryPO = this.toPO(userCategoryAgg);
        userCategoryMapper.updateById(userCategoryPO);
    }

}
