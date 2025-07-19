package com.zt.bookkeeping.ledger.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "task.pool")
public class TaskThreadPoolThirdCooperateConfig {

    /**
     * 核心线程数（默认线程数）
     */
    private int corePoolSize;

    /**
     * 最大线程数
     */
    private int maxPoolSize;

    /**
     * 允许线程空闲时间（单位：默认为秒）
     */
    private int keepAliveTime;

    /**
     * 缓冲队列数
     */
    private int queueCapacity;

    /**
     * 线程池名前缀
     */
    private String threadNamePrefix;
}
