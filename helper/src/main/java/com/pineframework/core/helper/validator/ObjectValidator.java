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
 * The {@link ObjectValidator} class provides utility functions for objects.
 * <ul>
 *   <li>{@link #requireNonNull(Object)}</li>
 *   <li>{@link #requireNonNull(Object, String)}</li>
 * </ul>
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public final class ObjectValidator {

  private ObjectValidator() {

  }

  /**
   * The {@code requireNonNull} method returns the object which is not {@code null}.
   *
   * @param object object
   * @throws IllegalArgumentException if {@code object} is {@code null}
   */
  public static <T> T requireNonNull(T object) {
    return requireNonNull(object, i18n("error.validation.should.not.be.null", i18n("var.name.data")));
  }

  /**
   * The {@code requireNonNull} method returns the object which is not {@code null}.
   *
   * @param object  object
   * @param message message of exception
   * @throws IllegalArgumentException if {@code object} is {@code null}
   */
  public static <T> T requireNonNull(T object, String message) {
    if (object == null) {
      throw new IllegalArgumentException(message);
    }

    return object;
  }

}
