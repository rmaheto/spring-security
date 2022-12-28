package com.example.springsecurity.shared.restframework.logging;

import com.google.common.base.Joiner;

import java.util.LinkedHashMap;

/**
 * Provides ability to output values as a key-value pair String
 */
public class LoggingMap extends LinkedHashMap<String, String> {

  public static final String CONSTANT_SEPARATOR_CHAR = "|";
  public static final String KEY_VALUE_SEPARATOR = "=";

  @Override
  public String toString() {
    return CONSTANT_SEPARATOR_CHAR + Joiner.on(CONSTANT_SEPARATOR_CHAR).withKeyValueSeparator(KEY_VALUE_SEPARATOR).useForNull("").join(this)
        + CONSTANT_SEPARATOR_CHAR;
  }
}
