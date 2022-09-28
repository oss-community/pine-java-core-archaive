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
import static com.pineframework.core.helper.validator.StringValidator.requireNonEmptyOrNull;

import com.pineframework.core.helper.YesNoQuestion;

/**
 * The {@link DateTimeValidator} class provides utility functions for date and time validations.
 * <ul>
 *   <li>{@link #isDate(String)}</li>
 *   <li>{@link #isTime(String)}</li>
 *   <li>{@link #isDateTime(String)}</li>
 * </ul>
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @since 2022-01-01
 */
public final class DateTimeValidator {

  private DateTimeValidator() {
  }

  /**
   * The {@code isDate} method checks the input string {@code date}
   * is a date or no. It returns {@link YesNoQuestion} include true
   * if the format of string date is {@literal yyyy-MM-dd}.
   *
   * @param date string date, {@literal yyyy-MM-dd}
   * @return {@link YesNoQuestion}
   * @throws IllegalArgumentException if {@code date} is {@code null} or empty
   */
  public static YesNoQuestion isDate(String date) {
    requireNonEmptyOrNull(date, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.date")));

    return ask(date.matches("^\\d{4}-\\d{2}-\\d{2}$"));
  }

  /**
   * The {@code isTime} method checks the input string {@code time}
   * is a time or no. It returns {@link YesNoQuestion} include true
   * if the format of string date is {@literal HH:mm:ss}.
   *
   * @param time string time, {@literal HH:mm:ss}
   * @return {@link YesNoQuestion}
   * @throws IllegalArgumentException if {@code time} is {@code null} or empty
   */
  public static YesNoQuestion isTime(String time) {
    requireNonEmptyOrNull(time, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.time")));

    return ask(time.matches("^\\d{2}:\\d{2}:\\d{2}$") || time.matches("^([+-])?\\d{2}:\\d{2}(:\\d{2})?$"));
  }

  /**
   * The {@code isDateTime} method checks the input string {@code datetime}
   * is a datetime or no. It returns {@link YesNoQuestion} include true
   * if the format of string date is {@literal yyyy-MM-dd'T'HH:mm:ss}.
   *
   * @param datetime string datetime, {@literal yyyy-MM-dd'T'HH:mm:ss}
   * @return {@link YesNoQuestion}
   * @throws IllegalArgumentException if {@code datetime} is {@code null} or empty
   */
  public static YesNoQuestion isDateTime(String datetime) {
    requireNonEmptyOrNull(datetime, i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.dataAndTime")));

    return ask(datetime.matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$"));
  }
}
