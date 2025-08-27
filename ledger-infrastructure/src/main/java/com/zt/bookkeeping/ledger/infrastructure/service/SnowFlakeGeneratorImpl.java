package com.zt.bookkeeping.ledger.infrastructure.service;

import com.zt.bookkeeping.ledger.domain.generator.SnowFlakeGenerator;
import com.zt.bookkeeping.ledger.infrastructure.config.SnowFlakeGeneratorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author thepatter
 */
@Component
public class SnowFlakeGeneratorImpl implements SnowFlakeGenerator {

    private static final int FIRST = 0; //首位 符号位 表示正负0为正 1为负

    private static final int TIME_LENGTH = 41; //毫秒时间戳位数 二进制的位数

    private static final int MACHINE_LENGTH = 5; //机器码位数 二进制的位数

    private static final int BUSINESS_LENGTH = 5; //业务位数 二进制的位数

    private static final int SEQUENCE_LENGTH = 12; //序列号位数 二进制的位数

    //时间戳偏移量
    private static final int TIME_SHIFT = MACHINE_LENGTH + BUSINESS_LENGTH + SEQUENCE_LENGTH;
    //机器位偏移量
    private static final int MACHINE_SHIFT = BUSINESS_LENGTH + SEQUENCE_LENGTH;

    // 序列号最大值
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_LENGTH);

    private long machineId = 1;

    //上一次发号时间
    private long oldTime;

    private AtomicInteger sequence = new AtomicInteger(0);

    private final Map<String, Integer> businessMap = new HashMap<>();

    @Autowired
    public SnowFlakeGeneratorImpl(SnowFlakeGeneratorConfig config) {
        long machineId = config.getMachineId();
        if (machineId >= (1 << MACHINE_LENGTH)) {
            throw new IllegalArgumentException("Machine ID can't be greater than " + (1 << MACHINE_LENGTH));
        }

        this.machineId = machineId;
        //初始化时间戳
        this.oldTime = getCurrentTime();

        businessMap.put("ledger", 1);
        businessMap.put("transaction", 2);
        businessMap.put("user_category", 3);
        businessMap.put("sys_category", 4);
    }

    /**
     * 获取毫秒级时间戳
     */
    private long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * 获取业务id
     */
    private long getBusinessId(String businessType) {
        return businessMap.get(businessType);
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间戳
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = getCurrentTime();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentTime();
        }
        return timestamp;
    }


    @Override
    public synchronized String nextId(String businessType) {
        long currentTime = getCurrentTime();
        if (currentTime < oldTime) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        } else if (currentTime == oldTime) {
            //在同一毫秒内创建，序号递增
            int sequence = this.sequence.incrementAndGet();
            if (sequence > SEQUENCE_MASK) {
                // 当前毫秒内序列号已达最大值，阻塞等待下一毫秒
                currentTime = tilNextMillis(oldTime);
            }
        } else {
            //到达下一个时间，重置序号
            this.sequence.set(0);
        }

        long businessId = this.getBusinessId(businessType);

        //写入时间
        oldTime = currentTime;

        long no = currentTime << TIME_SHIFT | machineId << MACHINE_SHIFT | businessId << SEQUENCE_LENGTH | sequence.get();
        return String.valueOf(no);
    }

}
