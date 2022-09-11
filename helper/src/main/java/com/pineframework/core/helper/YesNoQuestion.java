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

/**
 * The {@link YesNoQuestion} class creates ability to handle a
 * conditional situation as a functional style programming.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public record YesNoQuestion(boolean value) {

  /**
   * The {@code ask} method is a static factory method to create
   * a new instance of {@link YesNoQuestion}.
   *
   * @param value boolean value
   * @return {@link YesNoQuestion}
   */
  public static YesNoQuestion ask(boolean value) {
    return new YesNoQuestion(value);
  }

  /**
   * The {@code yes} method executes the task if the result of
   * condition is {@code true}.
   *
   * @param logic {@link Runnable}
   * @return {@link YesNoQuestion}
   * @throws NullPointerException if {@code logic} is {@code null}
   */
  public YesNoQuestion yes(Runnable logic) {
    requireNonNull(logic, i18n("error.validation.should.not.be.null", i18n("var.name.logic")));

    if (value) {
      logic.run();
    }
    return this;
  }

  /**
   * the {@code no} method executes the task if the result of
   * condition is {@code false}.
   *
   * @param logic {@link Runnable}
   * @return {@link YesNoQuestion}
   * @throws NullPointerException if {@code logic} is {@code null}
   */
  public YesNoQuestion no(Runnable logic) {
    requireNonNull(logic, i18n("error.validation.should.not.be.null", i18n("var.name.logic")));

    if (!value) {
      logic.run();
    }
    return this;
  }
}
