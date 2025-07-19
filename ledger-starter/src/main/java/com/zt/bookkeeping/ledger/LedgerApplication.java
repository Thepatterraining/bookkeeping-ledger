package com.zt.bookkeeping.ledger;

import com.zt.bookkeeping.ledger.infrastructure.config.TaskThreadPoolThirdCooperateConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan({"com.zt.bookkeeping.ledger.**.db"})
@EnableConfigurationProperties({TaskThreadPoolThirdCooperateConfig.class})
@EnableAsync
public class LedgerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LedgerApplication.class, args);
	}

}
