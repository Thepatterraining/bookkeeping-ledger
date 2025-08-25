package com.zt.bookkeeping.ledger.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zt.bookkeeping.ledger.domain.sysCategory.entity.SysCategoryAgg;
import com.zt.bookkeeping.ledger.domain.sysCategory.repository.SysCategoryRepository;
import com.zt.bookkeeping.ledger.domain.userCategory.entity.UserCategoryAgg;
import com.zt.bookkeeping.ledger.domain.userCategory.repository.UserCategoryRepository;
import com.zt.bookkeeping.ledger.infrastructure.db.LedgerBudgetMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.SysCategoryMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.UserCategoryMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.SysCategoryPO;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.UserCategoryPO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SysCategoryRepositoryImpl implements SysCategoryRepository {

    @Resource
    private SysCategoryMapper sysCategoryMapper;

    @Override
    public void insert(SysCategoryAgg sysCategoryAgg) {
        // 插入用户分类信息
        SysCategoryPO sysCategoryPO = this.toPO(sysCategoryAgg);
        sysCategoryMapper.insert(sysCategoryPO);
    }

    private SysCategoryPO toPO(SysCategoryAgg sysCategoryAgg) {
        return SysCategoryPO.builder()
                .id(sysCategoryAgg.getId())
                .categoryNo(sysCategoryAgg.getCategoryNo())
                .categoryName(sysCategoryAgg.getCategoryName())
                .categoryIcon(sysCategoryAgg.getCategoryIcon())
                .categoryLevel(sysCategoryAgg.getCategoryLevel())
                .categoryDesc(sysCategoryAgg.getCategoryDesc())
                .parentNo(sysCategoryAgg.getParentNo())
                .createTime(sysCategoryAgg.getCreateTime())
                .updateTime(sysCategoryAgg.getUpdateTime())
                .build();
    }

    private SysCategoryAgg toEntity(SysCategoryPO sysCategoryPO) {
        SysCategoryAgg sysCategoryAgg = SysCategoryAgg.builder()
                .id(sysCategoryPO.getId())
                .categoryNo(sysCategoryPO.getCategoryNo())
                .categoryName(sysCategoryPO.getCategoryName())
                .categoryIcon(sysCategoryPO.getCategoryIcon())
                .categoryLevel(sysCategoryPO.getCategoryLevel())
                .categoryDesc(sysCategoryPO.getCategoryDesc())
                .parentNo(sysCategoryPO.getParentNo())
                .createTime(sysCategoryPO.getCreateTime())
                .updateTime(sysCategoryPO.getUpdateTime())
                .build();
        return sysCategoryAgg;
    }

    @Override
    public SysCategoryAgg load(String categoryNo) {
        // 查询用户分类基本信息
        LambdaQueryWrapper<SysCategoryPO>  wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysCategoryPO::getCategoryNo, categoryNo);
        SysCategoryPO sysCategoryPO = sysCategoryMapper.selectOne(wrapper);
        if (sysCategoryPO == null) {
            return null;
        }
        return toEntity(sysCategoryPO);
    }

    @Override
    public List<SysCategoryAgg> loadAll() {
        List<SysCategoryPO> sysCategoryPOList = sysCategoryMapper.selectList(null);
        return sysCategoryPOList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public void update(SysCategoryAgg sysCategoryAgg) {
        // 更新用户分类信息
        SysCategoryPO sysCategoryPO = this.toPO(sysCategoryAgg);
        sysCategoryMapper.updateById(sysCategoryPO);
    }

}
