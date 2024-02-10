package com.customerconnect.data.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customerconnect.data.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
  boolean existsByFirstNameAndLastName(String firstName, String lastName);

  boolean existsByUuid(UUID uuid);

  boolean existsByEmail(String email);

  Optional<CustomerEntity> findByUuid(UUID uuid);

  Optional<CustomerEntity> findByEmail(String email);

  void deleteByUuid(UUID uuid);
}
