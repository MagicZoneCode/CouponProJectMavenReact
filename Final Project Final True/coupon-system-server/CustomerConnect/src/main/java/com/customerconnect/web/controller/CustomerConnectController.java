package com.customerconnect.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.customerconnect.data.dto.CustomerDto;
import com.customerconnect.service.CustomerConnectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerConnectController {

  private final CustomerConnectService customerConnectService;

  @PostMapping
  public CustomerDto addNewCustomer(@RequestHeader("Authorization") String token,
      @RequestBody CustomerDto customerDto) {
    return customerConnectService.addCustomer(token, customerDto);
  }

  @DeleteMapping
  public void deleteCustomer(@RequestHeader("Authorization") String token) {
    customerConnectService.removeClient(token);
  }

  @GetMapping("/details")
  public ResponseEntity<CustomerDto> getInfo(@RequestHeader("Authorization") String token) {
    return ResponseEntity.ok(customerConnectService.showDetails(token));
  }

  @GetMapping
  public ResponseEntity<List<CustomerDto>> findAllCustomers() {
    return ResponseEntity.ok(customerConnectService.getAllCustomers());
  }

  @PutMapping
  public CustomerDto updateCustomer(@RequestHeader("Authorization") String token,
      @RequestParam String firstName, @RequestParam String lastName) {
    return customerConnectService.updateCustomer(token, firstName, lastName);

  }

}
