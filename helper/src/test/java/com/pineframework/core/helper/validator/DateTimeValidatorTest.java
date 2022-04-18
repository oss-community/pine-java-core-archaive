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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pineframework.core.helper.AbstractUtilsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link DateTimeValidatorTest} class provides unit tests for {@link DateTimeValidator}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see DateTimeValidator
 * @since 2022-01-01
 */
@DisplayName("DateTime Validator Tests")
class DateTimeValidatorTest extends AbstractUtilsTest {

  @Test
  @DisplayName("if date is valid")
  void isDate_IfDateIsValid_ShouldReturnTrue() {
    //Given
    var givenDate = "2020-01-01";

    //When
    var result = DateTimeValidator.isDate(givenDate);

    //Then
    assertTrue(result.value());
  }

  @Test
  @DisplayName("if date is not valid")
  void isDate_IfDateIsNotValid_ShouldReturnTrue() {
    //Given
    var givenDate = "20-01-01";

    //When
    var result = DateTimeValidator.isDate(givenDate);

    //Then
    assertFalse(result.value());
  }

  @Test
  @DisplayName("if time is valid")
  void isTime_IfTimeIsValid_ShouldReturnTrue() {
    //Given
    var givenTime = "12:00:00";

    //When
    var result = DateTimeValidator.isTime(givenTime);

    //Then
    assertTrue(result.value());
  }

  @Test
  @DisplayName("if time is in offset format")
  void isTime_IfTimeIsInOffsetFormat_ShouldReturnTrue() {
    //Given
    var givenTime = "+01:00";

    //When
    var result = DateTimeValidator.isTime(givenTime);

    //Then
    assertTrue(result.value());
  }

  @Test
  @DisplayName("if time is not valid")
  void isTime_IfTimeIsNotValid_ShouldReturnTrue() {
    //Given
    var givenTime = "12:00:000";

    //When
    var result = DateTimeValidator.isTime(givenTime);

    //Then
    assertFalse(result.value());
  }

  @Test
  @DisplayName("if datetime is valid")
  void isDateTime_IfDatetimeIsValid_ShouldReturnTrue() {
    //Given
    var givenDatetime = "2020-01-01T12:00:00";

    //When
    var result = DateTimeValidator.isDateTime(givenDatetime);

    //Then
    assertTrue(result.value());
  }

  @Test
  @DisplayName("if datetime is not valid")
  void isDateTime_IfDatetimeIsNotValid_ShouldReturnTrue() {
    //Given
    var givenDatetime = "20-01-01 12:00:00";

    //When
    var result = DateTimeValidator.isDateTime(givenDatetime);

    //Then
    assertFalse(result.value());
  }
}
