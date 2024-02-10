package com.customerconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomerConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerConnectApplication.class, args);
    System.out.println("CustomerConnect is running!");
	}

}
