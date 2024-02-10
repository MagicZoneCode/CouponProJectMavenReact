package com.couponhub.data.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.CharJdbcType;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @EqualsAndHashCode.Include
  @JdbcType(CharJdbcType.class)
  private UUID uuid;
  @JdbcType(CharJdbcType.class)
  private UUID companyUuid;
  private int category;
  private String title;
  private LocalDate startDate;
  private LocalDate endDate;
  private int amount;
  private BigDecimal price;
  private String description;
  private String image;

  @ElementCollection
  @CollectionTable(name = "purchase", joinColumns = @JoinColumn(name = "coupon_id"))
  @JdbcType(CharJdbcType.class)
  @Column(name = "customer_uuid")
  private Set<UUID> customers;
}
