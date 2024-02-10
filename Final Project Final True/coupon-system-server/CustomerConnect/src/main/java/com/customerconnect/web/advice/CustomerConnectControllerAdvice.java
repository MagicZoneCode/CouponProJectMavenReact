package com.customerconnect.web.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import com.customerconnect.data.exception.CustomerConnectException;

@RestControllerAdvice
public class CustomerConnectControllerAdvice {

  @ExceptionHandler(CustomerConnectException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ProblemDetail handleCustomerConnectException(CustomerConnectException ex) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler(HttpClientErrorException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ProblemDetail handleHttpClientErrorException(HttpClientErrorException ex) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ProblemDetail handleGeneralErrors(Exception e) {
      return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
  }

}
