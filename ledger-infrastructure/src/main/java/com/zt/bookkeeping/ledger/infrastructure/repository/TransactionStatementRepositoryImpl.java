package com.zt.bookkeeping.ledger.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.CategorySnapshotVO;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.CategoryVO;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.TransactionStatementAgg;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.TransactionTypeVO;
import com.zt.bookkeeping.ledger.domain.transactionStatement.repository.TransactionStatementRepository;
import com.zt.bookkeeping.ledger.infrastructure.db.SysCategoryMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.TransactionStatementMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.SysCategoryPO;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.TransactionStatementPO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TransactionStatementRepositoryImpl implements TransactionStatementRepository {

    @Resource
    private TransactionStatementMapper transactionStatementMapper;

    @Resource
    private SysCategoryMapper sysCategoryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(TransactionStatementAgg transactionStatementAgg) {
        // 插入交易流水信息
        TransactionStatementPO ledgerPO = this.toPO(transactionStatementAgg);
        transactionStatementMapper.insert(ledgerPO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(CategoryVO categoryVO) {
        // 插入分类信息
        SysCategoryPO sysCategoryPO = this.toPO(categoryVO);
        sysCategoryMapper.insert(sysCategoryPO);
    }

    private SysCategoryPO toPO(CategoryVO categoryVO) {
        return SysCategoryPO.builder()
                .categoryNo(categoryVO.getCategoryNo())
                .categoryName(categoryVO.getCategoryName())
                .categoryLevel(categoryVO.getCategoryLevel())
                .categoryDesc(categoryVO.getCategoryDesc())
                .categoryIcon(categoryVO.getCategoryIcon())
                .parentNo(categoryVO.getParentNo())
                .build();
    }

    private TransactionStatementPO toPO(TransactionStatementAgg agg) {
        return TransactionStatementPO.builder()
                .id(agg.getId())
                .amount(agg.getAmount())
                .transactionStatementNo(agg.getTransactionStatementNo())
                .ledgerNo(agg.getLedgerNo())
                .transactionType(agg.getTransactionType().getCode())
                .transactionTime(agg.getTransactionTime())
                .transactionDesc(agg.getTransactionDesc())
                .transactionStatus(agg.getTransactionStatus())
                .categoryNo(agg.getCategorySnapshot().getCategoryNo())
                .categoryName(agg.getCategorySnapshot().getCategoryName())
                .categoryType(agg.getCategorySnapshot().getSourceType())
                .categoryIcon(agg.getCategorySnapshot().getCategoryIcon())
                .createTime(agg.getCreateTime())
                .updateTime(agg.getUpdateTime())
                .isDeleted(agg.getDeleted())
                .build();
    }

    @Override
    public void update(TransactionStatementAgg agg) {
        // 更新交易流水信息
        TransactionStatementPO ledgerPO = this.toPO(agg);
        transactionStatementMapper.updateById(ledgerPO);
    }

    @Override
    public TransactionStatementAgg load(String transactionStatementNo) {
        LambdaQueryWrapper<TransactionStatementPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TransactionStatementPO::getTransactionStatementNo, transactionStatementNo)
                .last("limit 1");
        TransactionStatementPO transactionStatementPO = transactionStatementMapper.selectOne(queryWrapper);
        return this.toAgg(transactionStatementPO);
    }

    private TransactionStatementAgg toAgg(TransactionStatementPO po) {
        return TransactionStatementAgg.builder()
                .id(po.getId())
                .amount(po.getAmount())
                .transactionStatementNo(po.getTransactionStatementNo())
                .ledgerNo(po.getLedgerNo())
                .transactionType(TransactionTypeVO.of(po.getTransactionType()))
                .transactionTime(po.getTransactionTime())
                .transactionDesc(po.getTransactionDesc())
                .transactionStatus(po.getTransactionStatus())
                .categorySnapshot(new CategorySnapshotVO(po.getCategoryName(), po.getCategoryNo(), po.getCategoryType(), po.getCategoryIcon()))
                .createTime(po.getCreateTime())
                .updateTime(po.getUpdateTime())
                .build();
    }


}
