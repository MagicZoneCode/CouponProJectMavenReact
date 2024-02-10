package com.couponhub.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.couponhub.controller.CouponHubController;
import com.couponhub.exception.CouponHubException;


@ControllerAdvice(assignableTypes = CouponHubController.class)
public class CouponHubControllerAdvice {

  @ExceptionHandler(CouponHubException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ProblemDetail handleCustomerConnectException(CouponHubException ex) {
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
