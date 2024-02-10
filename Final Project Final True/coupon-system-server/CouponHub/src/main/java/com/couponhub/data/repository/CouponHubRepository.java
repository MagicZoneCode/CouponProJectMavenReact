package com.couponhub.data.repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.couponhub.data.entity.CouponEntity;

public interface CouponHubRepository extends JpaRepository<CouponEntity, Long> {

  Optional<CouponEntity> findByUuid(UUID uuid);

  Set<CouponEntity> findByCompanyUuid(UUID companyUuid);

  // Get all coupons of customer by UUID (if it contains any)
  Set<CouponEntity> findByCustomersContaining(UUID customerUuid);

  Set<CouponEntity> findByCustomersNotContaining(UUID customerUuid);


  @Modifying
  @Query(value = "DELETE FROM purchase WHERE customer_uuid = ?", nativeQuery = true)
  void deleteCustomerHistoryPurchase(String customerUuid);
}
