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
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * The {@link StringValidator} class provides utility functions for string validations.
 * <ul>
 *   <li>{@link #isEmptyOrNull(String)} }</li>
 *   <li>{@link #isNotEmptyOrNull(String)}</li>
 *   <li>{@link #requireNonEmptyOrNull(String)}</li>
 *   <li>{@link #requireNonEmptyOrNull(String, String)}</li>
 * </ul>
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public final class StringValidator {

  private StringValidator() {
  }

  /**
   * The {@code isEmptyOrNull} method returns {@code true} if the {@code str} is {@code null}
   * otherwise returns {@code false}.
   *
   * @param str {@link String}
   * @return {@code true} if the {@code str} is {@code null} otherwise {@code false}
   */
  public static boolean isEmptyOrNull(String str) {
    return isNull(str) || str.isEmpty();
  }

  /**
   * The {@code isNotEmptyOrNull} method returns {@code true} if the {@code str} is {@code not null} otherwise returns {@code false}.
   *
   * @param str {@link String}
   * @return {@code true} if the {@link String} is {@code  not null} otherwise {@code false}
   */
  public static boolean isNotEmptyOrNull(String str) {
    return nonNull(str) && !str.isEmpty();
  }

  /**
   * The {@code requireNonEmptyOrNull} method checks the {@code str} is not {@code null} and not empty.
   * <p>This method is designed primarily for doing parameter validation in methods and constructors.</p>
   *
   * @param str {@link String}
   * @throws IllegalArgumentException if {@code str} is {@code null} or is <i>empty</i>
   */
  public static void requireNonEmptyOrNull(String str) {
    if (isEmptyOrNull(str)) {
      throw new IllegalArgumentException(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.parameter")));
    }
  }

  /**
   * The {@code requireNonEmptyOrNull} method checks the {@code str} is not {@code null} and not empty.
   * <p>This method is designed primarily for doing parameter validation in methods and constructors.</p>
   *
   * @param str {@link String}
   * @throws IllegalArgumentException if {@code str} is {@code null} or is <i>empty</i>
   */
  public static void requireNonEmptyOrNull(String str, String message) {
    if (isEmptyOrNull(str)) {
      throw new IllegalArgumentException(message);
    }
  }
}
