package com.couponhub.services;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.couponhub.data.dto.CouponHubDto;
import com.couponhub.data.dto.UserDto;
import com.couponhub.data.entity.CouponEntity;
import com.couponhub.data.mapper.CouponHubMapper;
import com.couponhub.data.repository.CouponHubRepository;
import com.couponhub.exception.CouponHubException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {
  private final CouponHubRepository couponHubRepository;
  private final RestTemplate restTemplate;
  private final CouponHubMapper couponHubMapper;

  public CouponHubDto getCoupon(String token, UUID couponUuid) {
    validateToken(token);
    CouponEntity couponEntity = couponHubRepository.findByUuid(couponUuid)
        .orElseThrow(() -> new CouponHubException("Coupon with uuid " + couponUuid + " does not exist"));
    return couponHubMapper.toDto(couponEntity);
  }

  public Set<CouponHubDto> getAllCustomerCoupons(String token) {
    UserDto userDto = validateToken(token);
    Set<CouponEntity> couponEntities = couponHubRepository.findByCustomersContaining(userDto.getUuid());
    return couponHubMapper.toDtoSet(couponEntities);
  }

  public Set<CouponHubDto> getAllCompanyCoupons(String token, UUID companyUuid) {
    validateToken(token);
    Set<CouponEntity> couponEntities = couponHubRepository.findByCompanyUuid(companyUuid);
    return couponHubMapper.toDtoSet(couponEntities);
  }

  @Transactional
  public CouponHubDto purchaseCoupon(String token, UUID couponUuid) {
    UserDto userDto = validateToken(token);
    CouponEntity couponEntity = couponHubRepository.findByUuid(couponUuid)
        .orElseThrow(() -> new CouponHubException("Coupon with uuid " + couponUuid + " does not exist"));
    if (couponEntity.getAmount() <= 0) {
      throw new CouponHubException("Coupon with uuid " + couponUuid + " is out of stock");
    }

    if (couponEntity.getCustomers().contains(userDto.getUuid())) {
      throw new CouponHubException(
          "Coupon with uuid " + couponUuid + " is already purchased by user with uuid " + userDto.getUuid());
    }

    if (couponEntity.getEndDate().isBefore(LocalDate.now())) {
      throw new CouponHubException("Coupon with uuid " + couponUuid + " is expired");
    }

    couponEntity.setAmount(couponEntity.getAmount() - 1);
    couponEntity.getCustomers().add(userDto.getUuid());
    return couponHubMapper.toDto(couponEntity);
  }

  @Transactional
  public void deleteCustomerPurchasedCouponHistory(UUID customerUuid) {
    couponHubRepository.deleteCustomerHistoryPurchase(customerUuid.toString());
  }

  private UserDto validateToken(String token) {
    String url = "http://auth-forge/parse-token";

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", token);

    HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

    ResponseEntity<UserDto> response = restTemplate.exchange(
        url, HttpMethod.GET, requestEntity, UserDto.class);

    return response.getBody();
  }

  public Set<CouponHubDto> getAllCoupons(String token){
    UserDto userDto = validateToken(token);
    Set<CouponEntity> couponsFromDb = couponHubRepository.findByCustomersNotContaining(userDto.getUuid());
    return couponHubMapper.toDtoSet(couponsFromDb);
  }

}
