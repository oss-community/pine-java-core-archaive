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

import java.util.Collection;

/**
 * The {@link CollectionValidator} class provides utility functions for collection validations.
 * <ul>
 *   <li>{@link #isEmptyOrNull(Collection)}</li>
 *   <li>{@link #isNotEmpty(Collection)}</li>
 *   <li>{@link #checkSize(Collection, int)}</li>
 *   <li>{@link #requireElement(Collection)}</li>
 * </ul>
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public final class CollectionValidator {

  private CollectionValidator() {
  }

  /**
   * The {@code isEmptyOrNull} method checks emptiness for a {@link Collection}
   * then returns {@code true} if the collection is {@code null} or empty
   * otherwise it returns {@code false}.
   *
   * @param collection {@link Collection}{@literal <}{@link T}{@literal >}
   * @param <T>        type of collection element
   * @return {@code true} if the {@code collection} is {@code null} or
   * empty otherwise it returns {@code false}
   */
  public static <T> boolean isEmptyOrNull(Collection<T> collection) {
    return isNull(collection) || collection.isEmpty();
  }

  /**
   * The {@code isNotEmpty} method checks emptiness for a {@link Collection}
   * then returns {@code true} if the collection has atleast an element
   * otherwise it returns {@code false}.
   *
   * @param collection {@link Collection}{@literal <}{@link E}{@literal >}
   * @param <E>        type of collection element
   * @return {@code true} if the {@code collection} has atleast one element
   * otherwise it returns {@code false}
   */
  public static <E> boolean isNotEmpty(Collection<E> collection) {
    return collection != null && !collection.isEmpty();
  }

  /**
   * The {@code checkSize} method checks equality for size of {@code collection}
   * and expected {@code size} then throws {@link IllegalArgumentException} if
   * they are not equals to each other, otherwise it continues.
   *
   * @param collection {@link Collection}{@literal <}{@link T}{@literal >}
   * @param <T>        type of collection
   * @param size       expected size for the collection
   * @throws IllegalArgumentException if size of collection and {@code size} parameter are not equals to each
   */
  public static <T> void checkSize(Collection<T> collection, int size) {
    ask(collection != null && collection.size() == size)
        .no(() -> {
          throw new IllegalArgumentException(
              i18n("error.validation.expectation", i18n("var.name.size"), (isNull(collection) ? "null" : collection.size()), size)
          );
        });
  }

  /**
   * The {@code requireElement} method checks emptiness for a {@link Collection}
   * then throws {@link IllegalArgumentException} if the collection is {@code null} or empty.
   *
   * @param collection {@link Collection}{@literal <}{@link T}{@literal >}
   * @param <T>        type of collection
   * @throws IllegalArgumentException if {@code collection} is {@code null} or empty
   */
  public static <T> void requireElement(Collection<T> collection) {
    ask(isNotEmpty(collection))
        .no(() -> {
          throw new IllegalArgumentException(i18n("error.validation.should.not.be.empty", i18n("var.name.collection")));
        });
  }
}
