package com.fraud.detection;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {
		"com.fraud.detection.mapper"
})
public class TestFraudDetectionApiCommonApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestFraudDetectionApiCommonApplication.class, args);
	}

}
