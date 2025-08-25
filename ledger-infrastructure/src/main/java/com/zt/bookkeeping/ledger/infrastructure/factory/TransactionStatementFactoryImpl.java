package com.zt.bookkeeping.ledger.infrastructure.factory;

import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerBudgetVO;
import com.zt.bookkeeping.ledger.domain.ledger.factory.LedgerFactory;
import com.zt.bookkeeping.ledger.domain.ledger.factory.TransactionStatementFactory;
import com.zt.bookkeeping.ledger.domain.sysCategory.entity.SysCategoryAgg;
import com.zt.bookkeeping.ledger.domain.sysCategory.repository.SysCategoryRepository;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.CategorySnapshotVO;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.CategoryVO;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.TransactionStatementAgg;
import com.zt.bookkeeping.ledger.domain.transactionStatement.entity.TransactionTypeVO;
import com.zt.bookkeeping.ledger.domain.transactionStatement.repository.TransactionStatementRepository;
import com.zt.bookkeeping.ledger.domain.userCategory.entity.UserCategoryAgg;
import com.zt.bookkeeping.ledger.domain.userCategory.repository.UserCategoryRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Component
public class TransactionStatementFactoryImpl implements TransactionStatementFactory {

    @Resource
    private TransactionStatementRepository transactionStatementRepository;

    @Resource
    private SysCategoryRepository sysCategoryRepository;

    @Resource
    private UserCategoryRepository userCategoryRepository;

    @Override
    public TransactionStatementAgg createTransactionStatementAgg(String ledgerNo, BigDecimal amount,
            Integer transactionType, String transactionDesc, String categoryNo, Integer categoryType, String userNo) {
        // 加载Category
        CategorySnapshotVO category;
        if (categoryType == 1) {
            // 系统分类
            SysCategoryAgg sysCategoryAgg = sysCategoryRepository.load(categoryNo);
            category = new CategorySnapshotVO(sysCategoryAgg.getCategoryName(), sysCategoryAgg.getCategoryNo(), categoryType);
        } else {
            // 用户分类
            UserCategoryAgg userCategoryAgg = userCategoryRepository.load(categoryNo, userNo);
            category = new CategorySnapshotVO(userCategoryAgg.getCategoryName(), userCategoryAgg.getCategoryNo(), categoryType);
        }
//        CategoryVO category = transactionStatementRepository.loadCategory(
//                categoryNo);
        return TransactionStatementAgg.builder()
                .ledgerNo(ledgerNo)
                .amount(amount.multiply(new BigDecimal(100)).intValue())
                .transactionType(TransactionTypeVO.of(transactionType))
                .transactionDesc(transactionDesc)
                .transactionStatementNo(UUID.randomUUID().toString())
                .categorySnapshot(category)
                .build();
    }

}
