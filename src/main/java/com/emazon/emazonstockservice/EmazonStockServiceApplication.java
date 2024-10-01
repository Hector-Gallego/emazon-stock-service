package com.emazon.emazonstockservice;

import com.emazon.emazonstockservice.configuration.security.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
@EnableFeignClients
public class EmazonStockServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmazonStockServiceApplication.class, args);
	}

}
