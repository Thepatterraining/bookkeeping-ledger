package com.zt.bookkeeping.ledger.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import com.zt.bookkeeping.ledger.domain.ledger.repository.LedgerRepository;
import com.zt.bookkeeping.ledger.infrastructure.db.LedgerMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.LedgerPO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

@Repository
public class LedgerRepositoryImpl implements LedgerRepository {

    @Resource
    private LedgerMapper ledgerMapper;

    @Override
    public void insert(LedgerAgg userAgg) {
        // 插入账本信息

        // 插入账本预算信息

    }

    public LedgerAgg findByNameInUser(String name, String userNo) {
        LambdaQueryWrapper<LedgerPO>  wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LedgerPO::getOwnerNo, userNo)
                .eq(LedgerPO::getLedgerName, name);
        LedgerPO ledgerPO = ledgerMapper.selectOne(wrapper);
        if (ledgerPO == null) {
            return null;
        }
        // todo
        LedgerAgg ledgerAgg = new LedgerAgg();
        BeanUtils.copyProperties(ledgerPO, ledgerAgg);
        return ledgerAgg;
    }

    @Override
    public LedgerAgg findByNoInUser(String ledgerNo, String userNo) {
        LambdaQueryWrapper<LedgerPO>  wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LedgerPO::getOwnerNo, userNo)
                .eq(LedgerPO::getLedgerNo, ledgerNo);
        LedgerPO ledgerPO = ledgerMapper.selectOne(wrapper);
        if (ledgerPO == null) {
            return null;
        }
        // todo
        LedgerAgg ledgerAgg = new LedgerAgg();
        BeanUtils.copyProperties(ledgerPO, ledgerAgg);
        return ledgerAgg;
    }

    @Override
    public void update(LedgerAgg ledgerAgg) {
        // 更新账本信息
        LedgerPO ledgerPO = new LedgerPO();
        BeanUtils.copyProperties(ledgerAgg, ledgerPO);
        ledgerMapper.updateById(ledgerPO);
    }

}
