package com.zt.bookkeeping.ledger.domain.transactionStatement.entity;

import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerStatusVO;
import lombok.Getter;

public class TransactionTypeVO {

    private final TransactionTypeEnum transactionType;

    public static final TransactionTypeVO INCOME = new TransactionTypeVO(TransactionTypeEnum.INCOME);
    public static final TransactionTypeVO EXPENDITURE = new TransactionTypeVO(TransactionTypeEnum.EXPENDITURE);

    private TransactionTypeVO(TransactionTypeEnum status) {
        this.transactionType = status;
    }

    public static TransactionTypeVO of(int code) {
        return new TransactionTypeVO(TransactionTypeEnum.fromCode(code));
    }

    public int getCode() {
        return transactionType.getCode();
    }

    public Boolean isExpenditure() {
        return TransactionTypeEnum.EXPENDITURE.equals(transactionType);
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof TransactionTypeVO that) && this.transactionType == that.transactionType;
    }

    @Override
    public int hashCode() {
        return transactionType.hashCode();
    }

    @Override
    public String toString() {
        return "transactionTypeVO{" +
                "transactionType =" + transactionType.getLabel() +
                '}';
    }

    @Getter
    public enum TransactionTypeEnum {
        INCOME(1, "收入"),
        EXPENDITURE(2, "支出"),
        ;

        private final int code;
        private final String label;

        TransactionTypeEnum(int code, String label) {
            this.code = code;
            this.label = label;
        }

        public static TransactionTypeVO.TransactionTypeEnum fromCode(int code) {
            for (TransactionTypeVO.TransactionTypeEnum e : values()) {
                if (e.code == code) return e;
            }
            throw new IllegalArgumentException("未知状态码: " + code);
        }
    }
}
