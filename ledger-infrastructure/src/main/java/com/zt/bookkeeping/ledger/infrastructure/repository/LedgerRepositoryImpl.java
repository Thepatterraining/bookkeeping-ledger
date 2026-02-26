package com.zt.bookkeeping.ledger.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zt.bookkeeping.ledger.domain.ledger.entity.*;
import com.zt.bookkeeping.ledger.domain.ledger.factory.LedgerFactory;
import com.zt.bookkeeping.ledger.domain.ledger.repository.LedgerRepository;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.TransactionTypeVO;
import com.zt.bookkeeping.ledger.infrastructure.db.LedgerBudgetMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.LedgerMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.LedgerMemberMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.TransactionStatementMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.LedgerBudgetPO;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.LedgerMemberPO;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.LedgerPO;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.TransactionStatementPO;
import com.zt.bookkeeping.ledger.infrastructure.util.LocalDateTimeUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class LedgerRepositoryImpl implements LedgerRepository {

    @Resource
    private LedgerMapper ledgerMapper;

    @Resource
    private LedgerBudgetMapper ledgerBudgetMapper;

    @Resource
    private LedgerMemberMapper ledgerMemberMapper;

    @Resource
    private TransactionStatementMapper transactionStatementMapper;

    @Resource
    private LedgerFactory ledgerFactory;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(LedgerAgg ledgerAgg) {
        // 插入账本信息
        LedgerPO ledgerPO = this.toPO(ledgerAgg);
        ledgerMapper.insert(ledgerPO);

        // 插入账本预算信息
        LedgerBudgetPO ledgerBudgetPO = toLedgerBudgetPO(ledgerAgg.getLastLedgerBudget());
        ledgerBudgetMapper.insert(ledgerBudgetPO);

        // 插入成员信息
        saveMemberSet(ledgerAgg.getMemberSet());
    }

    private void insertMember(Set<LedgerMemberEntity> memberSet) {
        LedgerMemberEntity memberEntity = memberSet.stream().findFirst().orElse(null);
        if (memberEntity == null) {
            return;
        }
        ledgerMemberMapper.insert(toMemberPO(memberEntity));
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
                .isDeleted(userAgg.getIsDeleted())
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
                .isDeleted(ledgerBudgetVO.getIsDeleted())
                .build();
    }

    private LedgerMemberPO toMemberPO(LedgerMemberEntity memberEntity) {
        return LedgerMemberPO.builder()
                .id(memberEntity.getId())
                .ledgerNo(memberEntity.getLedgerNo())
                .userNo(memberEntity.getUserNo())
                .role(memberEntity.getRole().getCode())
                .joinTime(memberEntity.getJoinTime())
                .status(memberEntity.getStatus().getCode())
                .isDeleted(memberEntity.getIsDeleted())
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
        return toEntity(ledgerPO, null, null, 0L, 0L);
    }

    private LedgerAgg toEntity(LedgerPO ledgerPO, LedgerBudgetPO ledgerBudgetPO, List<LedgerMemberPO> memberList, Long income, Long expense) {
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
        if (!CollectionUtils.isEmpty(memberList)) {
            Set<LedgerMemberEntity> memberSet = new HashSet<>();
            memberList.forEach(member -> {
                LedgerMemberEntity memberEntity = LedgerMemberEntity.builder()
                        .id(member.getId())
                        .ledgerNo(member.getLedgerNo())
                        .userNo(member.getUserNo())
                        .joinTime(member.getJoinTime())
                        .role(LedgerMemberRoleVO.of(member.getRole()))
                        .status(LedgerMemberStatusVO.of(member.getStatus()))
                        .createTime(member.getCreateTime())
                        .updateTime(member.getUpdateTime())
                        .build();
                memberSet.add(memberEntity);
            });
            ledgerAgg.setMemberSet(memberSet);
        }
        LedgerSummaryVO ledgerSummaryVO = ledgerFactory.createLedgerSummary(income, expense);
        ledgerAgg.setLastLedgerSummary(ledgerSummaryVO);
        return ledgerAgg;
    }

    @Override
    public LedgerAgg load(String ledgerNo) {
        // 查询账本基本信息
        LambdaQueryWrapper<LedgerPO>  wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LedgerPO::getLedgerNo, ledgerNo)
                .last("limit 1");
        LedgerPO ledgerPO = ledgerMapper.selectOne(wrapper);
        if (ledgerPO == null) {
            return null;
        }
        // 查询账本最新的预算信息
        LambdaQueryWrapper<LedgerBudgetPO> wrapperBudget = new LambdaQueryWrapper<>();
        wrapperBudget.eq(LedgerBudgetPO::getLedgerNo, ledgerPO.getLedgerNo())
                .orderByDesc(LedgerBudgetPO::getId)
                .last("limit 1");
        LedgerBudgetPO ledgerBudgetPO = ledgerBudgetMapper.selectOne(wrapperBudget);

        // 加载成员信息
        LambdaQueryWrapper<LedgerMemberPO> wrapperMember = new LambdaQueryWrapper<>();
        wrapperMember.eq(LedgerMemberPO::getLedgerNo, ledgerPO.getLedgerNo())
                .orderByDesc(LedgerMemberPO::getId)
                .last("limit 10");
        List<LedgerMemberPO> memberList = ledgerMemberMapper.selectList(wrapperMember);

        // 加载汇总信息
        Long incomeAmount = transactionStatementMapper.getSummaryAmount(ledgerPO.getLedgerNo(), TransactionTypeVO.INCOME.getCode());
        Long expenditureAmount = transactionStatementMapper.getSummaryAmount(ledgerPO.getLedgerNo(), TransactionTypeVO.EXPENDITURE.getCode());
        return toEntity(ledgerPO, ledgerBudgetPO, memberList, incomeAmount, expenditureAmount);
    }

    @Override
    public void update(LedgerAgg ledgerAgg) {
        // 插入账本信息
        LedgerPO ledgerPO = this.toPO(ledgerAgg);
        ledgerMapper.updateById(ledgerPO);

        // 插入账本预算信息
        LedgerBudgetPO ledgerBudgetPO = toLedgerBudgetPO(ledgerAgg.getLastLedgerBudget());
        // 查询预算是否存在，决定插入还是更新
        LambdaQueryWrapper<LedgerBudgetPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LedgerBudgetPO::getLedgerNo, ledgerBudgetPO.getLedgerNo())
                .eq(LedgerBudgetPO::getBudgetDate, LocalDateTimeUtil.format(ledgerBudgetPO.getBudgetDate(), LocalDateTimeUtil.DATE_FORMATTER_MONTH_ONE));
        LedgerBudgetPO budget = ledgerBudgetMapper.selectOne(wrapper);
        if (budget == null) {
            ledgerBudgetMapper.insert(ledgerBudgetPO);
        } else {
            budget.setBudgetAmount(ledgerBudgetPO.getBudgetAmount());
            budget.setUsedAmount(ledgerBudgetPO.getUsedAmount());
            budget.setRemainedAmount(ledgerBudgetPO.getRemainedAmount());
            ledgerBudgetMapper.updateById(budget);
        }

        // 更新成员信息
        saveMemberSet(ledgerAgg.getMemberSet());
    }

    private void saveMemberSet(Set<LedgerMemberEntity> memberSet) {
        if (CollectionUtils.isEmpty(memberSet)) {
            return;
        }
        memberSet.forEach(member -> {
            if (member.getId() == null) {
                ledgerMemberMapper.insert(toMemberPO(member));
            } else {
                ledgerMemberMapper.updateById(toMemberPO(member));
            }
        });
    }

    public Boolean exists(String ledgerNo) {
        LambdaQueryWrapper<LedgerPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LedgerPO::getLedgerNo, ledgerNo);
        return ledgerMapper.exists(wrapper);
    }

}
