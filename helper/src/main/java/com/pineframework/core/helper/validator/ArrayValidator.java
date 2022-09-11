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
import static com.pineframework.core.helper.YesNoQuestion.ask;
import static java.util.Objects.isNull;

/**
 * The {@link ArrayValidator} class provides utility functions for the array validations.
 * <ul>
 *  <li>{@link #isEmptyOrNull(Object[])}</li>
 *  <li>{@link #isNotEmptyOrNull(Object[])}</li>
 *  <li>{@link #checkSize(Object[], int)}</li>
 *  <li>{@link #requireElement(Object[])}</li>
 * </ul>
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public final class ArrayValidator {

  private ArrayValidator() {
  }

  /**
   * The {@code isEmptyOrNull} method checks emptiness for an array then
   * returns {@code true} if the array is {@code null} or empty otherwise,
   * returns {@code false}.
   *
   * @param array array
   * @param <E>   type of array
   * @return {@code boolean}
   */
  public static <E> boolean isEmptyOrNull(E[] array) {
    return array == null || array.length == 0;
  }

  /**
   * The {@code isNotEmptyOrNull} method checks emptiness for an array then
   * returns {@code true} if the array has atleast one element otherwise,
   * returns {@code false}.
   *
   * @param array array
   * @param <E>   type of array
   * @return {@code boolean}
   */
  public static <E> boolean isNotEmptyOrNull(E[] array) {
    return array != null && array.length >= 1;
  }

  /**
   * The {@code checkSize} method checks equality for size of {@code array} and
   * expected {@code expectedSize} then throws {@link IllegalArgumentException}
   * if they are not equals to each other, otherwise it continues.
   *
   * @param array        array
   * @param <T>          type of array
   * @param expectedSize expected size for the array
   * @throws IllegalArgumentException if size of {@code array} and expected size are not equals to each other
   */
  public static <T> void checkSize(T[] array, int expectedSize) {
    ask(array != null && array.length == expectedSize)
        .no(() -> {
          throw new IllegalArgumentException(
              i18n("error.validation.expectation", i18n("var.name.size"), (isNull(array) ? i18n("value.null") : array.length),
                  expectedSize));
        });
  }

  /**
   * The {@code requireElement} method checks emptiness for an array then
   * throws {@link IllegalArgumentException} if the array is {@code null}
   * or empty.
   * <p>
   * This method is designed primarily for applying as a validation.
   * </p>
   *
   * @param array array
   * @param <T>   type of array
   * @throws IllegalArgumentException if {@code array} is {@code null} or empty
   */
  public static <T> T[] requireElement(T[] array) {
    ask(isEmptyOrNull(array))
        .yes(() -> {
          throw new IllegalArgumentException(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.array")));
        });

    return array;
  }
}
