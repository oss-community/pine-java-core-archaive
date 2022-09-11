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

import static com.pineframework.core.helper.I18nUtils.i18n;
import static com.pineframework.core.helper.validator.ObjectValidator.requireNonNull;
import static java.util.EnumSet.allOf;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * The {@link EnumUtils} class provides utility functions for Enums.
 * <ul>
 *   <li>{@link #findEnumByFilter(Class, Predicate)} (Connection)}</li>
 * </ul>
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public final class EnumUtils {

  private EnumUtils() {

  }

  /**
   * The {@code findEnumByFilter} method finds enum instance by a filter.
   *
   * @param type   class object of {@link T}
   * @param filter {@link Predicate}
   * @param <T>    enum type
   * @return {@link Optional}
   * @throws NullPointerException if any parameter is {@code null}
   */
  public static <T extends Enum<T>> Optional<T> findEnumByFilter(Class<T> type, Predicate<T> filter) {
    requireNonNull(type, i18n("error.validation.should.not.be.null", i18n("var.name.type")));
    requireNonNull(filter, i18n("error.validation.should.not.be.null", i18n("var.name.filter")));

    return allOf(type)
        .stream()
        .filter(filter)
        .findFirst();
  }
}
