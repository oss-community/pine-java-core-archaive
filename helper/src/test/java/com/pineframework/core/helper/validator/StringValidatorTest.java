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
import static com.pineframework.core.helper.validator.StringValidator.isEmptyOrNull;
import static com.pineframework.core.helper.validator.StringValidator.isNotEmptyOrNull;
import static com.pineframework.core.helper.validator.StringValidator.requireNonEmptyOrNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pineframework.core.helper.AbstractUtilsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link StringValidatorTest} class provides unit tests for {@link StringValidator}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see StringValidator
 * @since 2022-01-01
 */
@DisplayName("String Validator Tests")
class StringValidatorTest extends AbstractUtilsTest {

  @Test
  @DisplayName("checking string emptiness if it is null")
  @SuppressWarnings("ConstantConditions")
  void isEmpty_WhenInputIsNull_ShouldReturnTrue() {
    //Given
    String givenString = null;

    //When
    var result = isEmptyOrNull(givenString);

    //Then
    assertTrue(result);
  }

  @Test
  @DisplayName("checking string emptiness if it is empty")
  void isEmpty_WhenInputIsEmpty_ShouldReturnTrue() {
    //Given
    var givenString = "";

    //When
    var result = isEmptyOrNull(givenString);

    //Then
    assertTrue(result);
  }

  @Test
  @DisplayName("checking string emptiness if it is not null and not empty")
  void isEmpty_WhenInputIsNonEmpty_ShouldReturnFalse() {
    //Given
    var givenString = "fake string";

    //When
    var result = isEmptyOrNull(givenString);

    //Then
    assertFalse(result);
  }

  @Test
  @DisplayName("checking string emptiness if it is not null and not empty")
  void notEmpty_WhenInputIsNonEmpty_ShouldReturnTrue() {
    //Given
    var givenString = "fake";

    //When
    boolean result = isNotEmptyOrNull(givenString);

    //Then
    assertTrue(result);
  }

  @Test
  @DisplayName("validating string emptiness if it is null")
  @SuppressWarnings("ConstantConditions")
  void requireNotEmpty_WhenInputIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    String givenString = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> requireNonEmptyOrNull(givenString));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.parameter")), message);
  }

  @Test
  @DisplayName("validating string emptiness if it is empty")
  void requireNotEmpty_WhenInputIsEmpty_ShouldThrowIllegalArgumentException() {
    //Given
    var givenString = "";

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> requireNonEmptyOrNull(givenString));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.parameter")), message);
  }

  @Test
  @DisplayName("validating string emptiness if it is not null and not empty")
  void requireNotEmpty_WhenInputIsNotEmpty_ShouldThrowNothing() {
    //Given
    var givenString = "fake";

    //When
    var result = shouldRunSuccessfully(() -> requireNonEmptyOrNull(givenString));

    //Then
    assertTrue(result);
  }
}
