package com.example.springsecurity.shared.restframework.utils;

/**
 * This class exists to allow easy mocking of {@link System#currentTimeMillis()} calls without negative side-effects.
 */
public final class SystemTimeUtils {

  private SystemTimeUtils() {
    //Prevent instantiation
  }

  /**
   * Wrapper for the {@link System#currentTimeMillis()} call to allow for easy mocking.
   *
   * @return The value of {@link System#currentTimeMillis()}
   */
  public static long getCurrentTimeMillis() {
    return System.currentTimeMillis();
  }
}
