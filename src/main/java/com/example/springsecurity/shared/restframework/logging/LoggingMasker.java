package com.example.springsecurity.shared.restframework.logging;

public interface LoggingMasker {

  String MASK_PATTERN = "******";

  default String mask(String toBeMasked) {
    return toBeMasked;
  }
}

