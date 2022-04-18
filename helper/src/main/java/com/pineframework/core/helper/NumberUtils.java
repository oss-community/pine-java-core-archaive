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

/**
 * {@link NumberUtils} class provides utility functions for numbers.
 * <ul>
 *   <li>{@link #getSignChar(int)}</li>
 *   <li>{@link #getSign(int)}</li>
 *   <li>{@link #getSign(String)}</li>
 * </ul>
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public final class NumberUtils {

  public static final String POSITIVE = "+";

  public static final String NEGATIVE = "-";

  private NumberUtils() {

  }

  /**
   * The {@code getSignChar} method returns {@code +} if number is
   * positive and, {@code -} if number is negative.
   * <p>
   * If number is zero the method returns empty string.
   * </p>
   *
   * @param num number
   * @return {@code +} or {@code -}
   */
  public static String getSignChar(int num) {
    if (num == 0) {
      return "";
    }
    return num > 0 ? "+" : "-";
  }

  /**
   * The {@code getSignChar} method returns {@code +1} if number is
   * zero or positive and, {@code -1} if number is negative.
   *
   * @param num number
   * @return {@code +1} or {@code -1}
   */
  public static int getSign(int num) {
    return num >= 0 ? 1 : -1;
  }

  /**
   * The {@code getSignChar} method returns {@code +1} if number is
   * zero or positive and, {@code -1} if number is negative.
   *
   * @param num number
   * @return {@code +1} or {@code -1}
   */
  public static int getSign(String num) {
    return Integer.parseInt(num) >= 0 ? 1 : -1;
  }
}
