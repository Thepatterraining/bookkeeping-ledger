package com.zt.bookkeeping.ledger.application.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

/**
 * Jackson配置类
 * 用于注册自定义的序列化和反序列化器
 */
@Configuration
public class JacksonConfig {

    /**
     * 注册自定义模块
     */
    @Bean
    public Module customModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDate.class, new YearMonthDateDeserializer());
        return module;
    }
}

