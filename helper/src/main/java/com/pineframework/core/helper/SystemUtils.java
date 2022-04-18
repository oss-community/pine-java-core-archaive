package com.pineframework.core.helper;


import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

import java.util.Optional;

/**
 * The {@link ArrayUtils} class provides utility functions for the Operating System.
 *
 * @author Saman Alishirishahrbabak
 */
public final class SystemUtils {

  private SystemUtils() {

  }

  /**
   * The {@code getEnv} method returns environment variables if there is existed in OS variables or {@code System.properties}, otherwise it
   * returns {@link Optional} empty.
   *
   * @param name environment variable
   * @return {@link Optional}
   */
  public static Optional<String> getEnv(String name) {
    if (System.getenv().containsKey(name)) {
      return ofNullable(System.getenv(name));

    } else if (System.getProperties().containsKey(name)) {
      return ofNullable(System.getProperty(name));

    } else {
      return empty();
    }
  }
}
