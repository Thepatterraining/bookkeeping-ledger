package com.zt.bookkeeping.ledger.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.CategoryVO;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.TransactionStatementAgg;
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
                .createTime(agg.getCreateTime())
                .updateTime(agg.getUpdateTime())
                .build();
    }

    @Override
    public void update(TransactionStatementAgg agg) {
        // 更新交易流水信息
        TransactionStatementPO ledgerPO = this.toPO(agg);
        transactionStatementMapper.updateById(ledgerPO);
    }


}
