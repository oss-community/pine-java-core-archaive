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

package com.pineframework.core.helper.validator;

import static com.pineframework.core.helper.I18nUtils.i18n;

/**
 * {@link NumberValidator} class provides utility functions for number validations.
 * <ul>
 *   <li>{@link #shouldBeGreaterThanZero(int)}</li>
 *   <li>{@link #shouldBeGreaterThanZero(int, String)}</li>
 *   <li>{@link #shouldBeLessThanZero(int)} (int, String)}</li>
 *   <li>{@link #shouldBeLessThanZero(int, String)} (int, String)}</li>
 * </ul>
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public final class NumberValidator {

  private NumberValidator() {

  }

  /**
   * The {@code shouldBeGreaterThanZero} method checks a number value
   * and if the number is less than zero then throws an exception.
   * <p>
   * This method design primarily for data validation.
   * </p>
   *
   * @param number number
   * @throws IllegalArgumentException if {@code number} is less than zero
   */
  public static void shouldBeGreaterThanZero(int number) {
    shouldBeGreaterThanZero(number, i18n("error.validation.should.be.greaterThan", i18n("var.name.number"), 0));
  }

  /**
   * The {@code shouldBeGreaterThanZero} method checks a number value
   * and if the number is less than zero then throws an exception.
   * <p>
   * This method design primarily for data validation.
   * </p>
   *
   * @param number  number
   * @param message message of exception
   * @throws IllegalArgumentException if {@code number} is less than zero
   */
  public static void shouldBeGreaterThanZero(int number, String message) {
    if (number < 0) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * The {@code shouldBeLessThanZero} method checks a number value
   * and if the number is less than zero then throws an exception.
   * <p>
   * This method design primarily for data validation.
   * </p>
   *
   * @param number number
   * @throws IllegalArgumentException if {@code number} is less than zero
   */
  public static void shouldBeLessThanZero(int number) {
    shouldBeLessThanZero(number, i18n("error.validation.should.be.lessThan", i18n("var.name.number"), 0));
  }

  /**
   * The {@code shouldBeLessThanZero} method checks a number value
   * and if the number is less than zero then throws an exception.
   * <p>
   * This method design primarily for data validation.
   * </p>
   *
   * @param number  number
   * @param message message of exception
   * @throws IllegalArgumentException if {@code number} is less than zero
   */
  public static void shouldBeLessThanZero(int number, String message) {
    if (number > 0) {
      throw new IllegalArgumentException(message);
    }
  }
}
