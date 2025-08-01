package com.zt.bookkeeping.ledger;

import com.zt.bookkeeping.ledger.infrastructure.config.TaskThreadPoolThirdCooperateConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan({"com.zt.bookkeeping.ledger.**.db"})
@EnableConfigurationProperties({TaskThreadPoolThirdCooperateConfig.class})
@EnableAsync
public class LedgerApplication {

	private static final Logger logger = LoggerFactory.getLogger(LedgerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LedgerApplication.class, args);
		logger.info("记账服务启动成功！");
	}

}
