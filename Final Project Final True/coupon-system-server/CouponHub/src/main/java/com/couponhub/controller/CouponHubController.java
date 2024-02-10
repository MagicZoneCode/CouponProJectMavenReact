package com.couponhub.controller;

import java.util.Set;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.couponhub.data.dto.CouponHubDto;
import com.couponhub.services.CouponService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponHubController {

  private final CouponService couponService;

  @GetMapping("/{couponUuid}")
  public ResponseEntity<CouponHubDto> findByUuid(@RequestHeader("Authorization") String token,
      @PathVariable UUID couponUuid) {
    return ResponseEntity.ok(couponService.getCoupon(token, couponUuid));
  }

  @GetMapping("/customer")
  public ResponseEntity<Set<CouponHubDto>> findAllCouponsBelongCustomer(@RequestHeader("Authorization") String token) {
    return ResponseEntity.ok(couponService.getAllCustomerCoupons(token));

  }

  @GetMapping("/company/{companyUuid}")
  public ResponseEntity<Set<CouponHubDto>> showAllCompanyCoupons(@RequestHeader("Authorization") String token,
      @PathVariable UUID companyUuid) {
    return ResponseEntity.ok(couponService.getAllCompanyCoupons(token, companyUuid));
  }

  @PostMapping("/purchase/{couponUuid}")
  public ResponseEntity<CouponHubDto> purchaseCoupon(@RequestHeader("Authorization") String token,
      @PathVariable UUID couponUuid) {
    return ResponseEntity.ok(couponService.purchaseCoupon(token, couponUuid));
  }

  @DeleteMapping("/{customerUuid}")
  public ResponseEntity<Void> deleteCustomerPurchasedCouponHistory(
      @PathVariable UUID customerUuid) {
    couponService.deleteCustomerPurchasedCouponHistory(customerUuid);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/all-coupons")
  public Set<CouponHubDto> getAllCoupons(@RequestHeader("Authorization") String token) {
    return couponService.getAllCoupons(token);
  }

}
