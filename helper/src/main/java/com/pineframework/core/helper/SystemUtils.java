/* **********************************************************************
 * Copyright (c) 2022 Saman Alishirishahrbabak.
 * All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ***********************************************************************/

package com.pineframework.core.helper;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

import java.util.Optional;

/**
 * The {@link SystemUtils} class provides utility functions for the Operating System.
 *
 * <ul>
 *   <li>{@link #getEnv(String)}</li>
 * </ul>
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
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
