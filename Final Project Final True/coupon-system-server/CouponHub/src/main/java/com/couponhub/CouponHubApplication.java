package com.couponhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CouponHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponHubApplication.class, args);
    System.out.println("CouponHub is running!");
	}

}
