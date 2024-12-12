package com.fraud.detection;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {
		"com.fraud.detection.mapper"
})
public class FraudDetectionFrontApplication {

	public static void main(String[] args) {
		SpringApplication.run(FraudDetectionFrontApplication.class, args);
	}

}
