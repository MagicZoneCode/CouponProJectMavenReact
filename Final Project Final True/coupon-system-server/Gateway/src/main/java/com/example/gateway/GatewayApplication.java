package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@SpringBootApplication
public class GatewayApplication {
  public static void main(String[] args) {

    SpringApplication.run(GatewayApplication.class, args);
    System.out.println("Gateway is running");
  }

  @Bean
  public CorsWebFilter corsFilter() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.addAllowedOrigin("*");
    corsConfig.addAllowedMethod("*");
    corsConfig.addAllowedHeader("*");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);
    return new CorsWebFilter(source);
  }

  @Bean
  public RouteLocator authForgeRoutes(RouteLocatorBuilder routeLocatorBuilder) {
    return routeLocatorBuilder.routes()
        // AuthForge routes
        .route("authorizationForgeRoute", r -> r.path("/register")
            .filters(f -> f.rewritePath("/register", "/sign-up"))
            .uri("lb://auth-forge"))
        .route("authenticationForgeRoute", r -> r.path("/authentication")
            .filters(f -> f.rewritePath("/authentication", "/login"))
            .uri("lb://auth-forge"))
        .route("parseTokenRoute", r -> r.path("/validate")
            .filters(f -> f.rewritePath("/validate", "/parse-token"))
            .uri("lb://auth-forge"))
        // CustomerConnect routes
        .route("addCustomerRoute", r -> r.path("/new-customer")
            .filters(f -> f.rewritePath("/new-customer", "/customers"))
            .uri("lb://customer-connect"))
        .route("deleteCustomerRoute", r -> r.path("/remove-customer")
            .filters(f -> f.rewritePath("/remove-customer", "/customers"))
            .uri("lb://customer-connect"))
        .route("customerDetailsRoute", r -> r.path("/get-details")
            .and().method(HttpMethod.GET)
            .filters(f -> f.rewritePath("/get-details", "/customers/details"))
            .uri("lb://customer-connect"))
        .route("allCustomersRoute", r -> r.path("/show-all")
            .filters(f -> f.rewritePath("/show-all", "/customers"))
            .uri("lb://customer-connect"))
        .route("UpdateCustomersRoute", r -> r.path("/update-customer")
            .filters(f -> f.rewritePath("/update-customer", "/customers"))
            .uri("lb://customer-connect"))
        // CouponHub routes
        .route("findCouponByUuidRoute", r -> r.path("/get-coupon")
            .filters(f -> f.rewritePath("/get-coupon", "/coupons/details"))
            .uri("lb://coupon-hub"))
        .route("findAllCustomerCouponRoute", r -> r.path("/all-customer-coupons")
            .filters(f -> f.rewritePath("/all-customer-coupons", "/coupons/customer"))
            .uri("lb://coupon-hub"))
        .route("companyCouponsRoute", r -> r.path("/all-company-coupons/{companyUuid}")
            .filters(f -> f.rewritePath("/all-company-coupons/(?<companyUuid>.*)", "/coupons/company/${companyUuid}"))
            .uri("lb://coupon-hub"))
        .route("buyCouponRoute", r -> r.path("/buy/{couponUuid}")
            .filters(f -> f.rewritePath("/buy/(?<couponUuid>.*)", "/coupons/purchase/${couponUuid}"))
            .uri("lb://coupon-hub"))
        .route("allCouponsRoute", r -> r.path("/all-coupons")
            .filters(f -> f.rewritePath("/all-coupons", "/coupons/all-coupons"))
            .uri("lb://coupon-hub"))
        .build();
  }
}
