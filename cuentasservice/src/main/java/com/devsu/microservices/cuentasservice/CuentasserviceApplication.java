package com.devsu.microservices.cuentasservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CuentasserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CuentasserviceApplication.class, args);
	}

}
