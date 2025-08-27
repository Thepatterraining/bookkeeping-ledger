package com.zt.bookkeeping.ledger.domain.generator;

public interface SnowFlakeGenerator {
    String nextId(String businessType);
}
