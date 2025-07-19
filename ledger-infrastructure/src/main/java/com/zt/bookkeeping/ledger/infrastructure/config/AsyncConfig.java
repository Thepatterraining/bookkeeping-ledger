package com.zt.bookkeeping.ledger.infrastructure.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class AsyncConfig implements AsyncConfigurer {
    @Resource
    private TaskThreadPoolThirdCooperateConfig config;
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("AsyncExecutor-");
        executor.setCorePoolSize(config.getCorePoolSize()); // 核心线程数
        executor.setMaxPoolSize(config.getMaxPoolSize()); // 最大线程数
        executor.setKeepAliveSeconds(config.getKeepAliveTime());
        executor.setQueueCapacity(config.getQueueCapacity()); // 队列容量
        // 直接在execute方法的调用线程中运行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 初始化
        executor.initialize();
        return executor;
    }
}
