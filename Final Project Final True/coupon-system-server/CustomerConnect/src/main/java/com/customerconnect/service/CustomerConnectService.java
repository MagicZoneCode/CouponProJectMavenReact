package com.customerconnect.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.customerconnect.data.dto.CustomerDto;
import com.customerconnect.data.dto.UserDto;
import com.customerconnect.data.entity.CustomerEntity;
import com.customerconnect.data.exception.CustomerConnectException;
import com.customerconnect.data.mapper.CustomerMapper;
import com.customerconnect.data.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerConnectService {
  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;
  private final RestTemplate restTemplate;

  @Transactional
  public CustomerDto addCustomer(String token, CustomerDto customerDto) {
    UserDto userDto = validateToken(token);

    if (customerRepository.existsByEmail(customerDto.getEmail())) {
      throw new CustomerConnectException("Customer with email " + customerDto.getEmail() + " already exists");
    }
    customerDto.setUuid(userDto.getUuid());
    customerRepository.save(customerMapper.toEntity(customerDto));
    return customerDto;
  }

  public CustomerDto showDetails(String token) {
    UserDto userDto = validateToken(token);
    CustomerEntity customerEntity = customerRepository.findByUuid(userDto.getUuid())
        .orElseThrow(() -> new CustomerConnectException("Customer with uuid " + userDto.getUuid() + " does not exist"));
    return customerMapper.toDto(customerEntity);
  }

  @Transactional
  public void removeClient(String token) {
    UserDto userDto = validateToken(token);
    if (!customerRepository.existsByUuid(userDto.getUuid())) {
      throw new CustomerConnectException("Customer with uuid " + userDto.getUuid() + " does not exist");
    }
    deleteCustomerPurchasedCouponHistory(userDto.getUuid());
    customerRepository.deleteByUuid(userDto.getUuid());
  }

  @Transactional
  public CustomerDto updateCustomer(String token, String firstName, String lastName) {
    UserDto userDto = validateToken(token);
    CustomerEntity customerEntity = customerRepository.findByUuid(userDto.getUuid())
        .orElseThrow(() -> new CustomerConnectException("Customer with uuid " + userDto.getUuid() + " does not exist"));

    customerEntity.setFirstName(firstName);
    customerEntity.setLastName(lastName);
    return customerMapper.toDto(customerEntity);
  }

  public List<CustomerDto> getAllCustomers() {
    List<CustomerEntity> customerEntities = customerRepository.findAll();
    return customerMapper.toDtoList(customerEntities);
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

  private void deleteCustomerPurchasedCouponHistory(UUID customerUuid) {
    // String url = "http://localhost:8080/coupons/" + customerUuid;
    String url = "http://coupon-hub/coupons/" + customerUuid;

    restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
  }

}
