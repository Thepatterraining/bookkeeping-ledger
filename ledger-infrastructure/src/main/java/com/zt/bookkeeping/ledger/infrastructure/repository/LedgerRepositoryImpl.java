package com.zt.bookkeeping.ledger.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerBudgetVO;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerStatusVO;
import com.zt.bookkeeping.ledger.domain.ledger.repository.LedgerRepository;
import com.zt.bookkeeping.ledger.infrastructure.db.LedgerBudgetMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.LedgerMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.LedgerBudgetPO;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.LedgerPO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class LedgerRepositoryImpl implements LedgerRepository {

    @Resource
    private LedgerMapper ledgerMapper;

    @Resource
    private LedgerBudgetMapper ledgerBudgetMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(LedgerAgg ledgerAgg) {
        // 插入账本信息
        LedgerPO ledgerPO = this.toPO(ledgerAgg);
        ledgerMapper.insert(ledgerPO);

        // 插入账本预算信息
        LedgerBudgetPO ledgerBudgetPO = toLedgerBudgetPO(ledgerAgg.getLastLedgerBudget());
        ledgerBudgetMapper.insert(ledgerBudgetPO);
    }

    private LedgerPO toPO(LedgerAgg userAgg) {
        return LedgerPO.builder()
                .id(userAgg.getId())
                .ledgerName(userAgg.getLedgerName())
                .ledgerNo(userAgg.getLedgerNo())
                .ledgerStatus(userAgg.getLedgerStatus().getCode())
                .ownerNo(userAgg.getOwnerNo())
                .ledgerDesc(userAgg.getLedgerDesc())
                .ledgerImage(userAgg.getLedgerImage())
                .build();
    }

    private LedgerBudgetPO toLedgerBudgetPO(LedgerBudgetVO ledgerBudgetVO) {
        return LedgerBudgetPO.builder()
                .id(ledgerBudgetVO.getId())
                .ledgerNo(ledgerBudgetVO.getLedgerNo())
                .budgetAmount(ledgerBudgetVO.getBudgetAmount())
                .usedAmount(ledgerBudgetVO.getUsedAmount())
                .remainedAmount(ledgerBudgetVO.getRemainedAmount())
                .budgetDate(ledgerBudgetVO.getBudgetDate())
                .build();
    }

    public LedgerAgg findByNameInUser(String name, String userNo) {
        // 查询账本基本信息
        LambdaQueryWrapper<LedgerPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LedgerPO::getLedgerName, name)
                .eq(LedgerPO::getOwnerNo, userNo);
        LedgerPO ledgerPO = ledgerMapper.selectOne(wrapper);
        if (ledgerPO == null) {
            return null;
        }
        return toEntity(ledgerPO, null);
    }

    private LedgerAgg toEntity(LedgerPO ledgerPO, LedgerBudgetPO ledgerBudgetPO) {
        LedgerAgg ledgerAgg = LedgerAgg.builder()
                .id(ledgerPO.getId())
                .ledgerName(ledgerPO.getLedgerName())
                .ledgerNo(ledgerPO.getLedgerNo())
                .ledgerStatus(LedgerStatusVO.of(ledgerPO.getLedgerStatus()))
                .ownerNo(ledgerPO.getOwnerNo())
                .ledgerDesc(ledgerPO.getLedgerDesc())
                .ledgerImage(ledgerPO.getLedgerImage())
                .createTime(ledgerPO.getCreateTime())
                .updateTime(ledgerPO.getUpdateTime())
                .build();
        // 预算信息
        if (ledgerBudgetPO != null) {
            LedgerBudgetVO ledgerBudgetVO = LedgerBudgetVO.builder()
                    .ledgerNo(ledgerBudgetPO.getLedgerNo())
                    .budgetAmount(ledgerBudgetPO.getBudgetAmount())
                    .usedAmount(ledgerBudgetPO.getUsedAmount())
                    .remainedAmount(ledgerBudgetPO.getRemainedAmount())
                    .budgetDate(ledgerBudgetPO.getBudgetDate())
                    .createTime(ledgerBudgetPO.getCreateTime())
                    .updateTime(ledgerBudgetPO.getUpdateTime())
                    .build();
            ledgerAgg.setLastLedgerBudget(ledgerBudgetVO);
        }
        return ledgerAgg;
    }

    @Override
    public LedgerAgg load(String ledgerNo) {
        // 查询账本基本信息
        LambdaQueryWrapper<LedgerPO>  wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LedgerPO::getLedgerNo, ledgerNo);
        LedgerPO ledgerPO = ledgerMapper.selectOne(wrapper);
        if (ledgerPO == null) {
            return null;
        }
        // 查询账本最新的预算信息
        LambdaQueryWrapper<LedgerBudgetPO> wrapperBudget = new LambdaQueryWrapper<>();
        wrapperBudget.eq(LedgerBudgetPO::getLedgerNo, ledgerPO.getLedgerNo())
                .orderByDesc(LedgerBudgetPO::getLedgerNo)
                .last("limit 1");
        LedgerBudgetPO ledgerBudgetPO = ledgerBudgetMapper.selectOne(wrapperBudget);
        return toEntity(ledgerPO, ledgerBudgetPO);
    }

    @Override
    public void update(LedgerAgg ledgerAgg) {
        // 插入账本信息
        LedgerPO ledgerPO = this.toPO(ledgerAgg);
        ledgerMapper.updateById(ledgerPO);

        // 插入账本预算信息
        LedgerBudgetPO ledgerBudgetPO = toLedgerBudgetPO(ledgerAgg.getLastLedgerBudget());
        ledgerBudgetMapper.updateById(ledgerBudgetPO);
    }

}
