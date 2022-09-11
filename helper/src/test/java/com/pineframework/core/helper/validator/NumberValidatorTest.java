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
import static com.pineframework.core.helper.validator.NumberValidator.shouldBeGreaterThanZero;
import static com.pineframework.core.helper.validator.NumberValidator.shouldBeLessThanZero;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pineframework.core.helper.AbstractUtilsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link NumberValidatorTest} class provides unit tests for {@link NumberValidator}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see NumberValidator
 * @since 2022-01-01
 */
@DisplayName("Number Validator Tests")
class NumberValidatorTest extends AbstractUtilsTest {

  @Test
  @DisplayName("checking the number should be greater than zero if number is less than zero")
  void shouldBeGreaterThanZero_IfNumberIsLessThanZero_ShouldThrowIllegalArgumentException() {
    //Given
    var givenNumber = -1;

    //Expectation
    var expectationException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectationException, () -> shouldBeGreaterThanZero(givenNumber));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.be.greaterThan", i18n("var.name.number"), 0), message);
  }

  @Test
  @DisplayName("checking the number should be greater than zero if number is greater than zero")
  void shouldBeGreaterThanZero_IfNumberIsGreaterThanZero_ShouldRunSuccessful() {
    //Given
    var givenNumber = 1;

    //When
    var result = shouldRunSuccessfully(() -> shouldBeGreaterThanZero(givenNumber));

    //Then
    assertTrue(result);
  }

  @Test
  @DisplayName("checking the number should be less than zero if number is greater than zero")
  void shouldBeLessThanZero_IfNumberIsLessThanZero_ShouldThrowIllegalArgumentException() {
    //Given
    var givenNumber = 1;

    //Expectation
    var expectationException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectationException, () -> shouldBeLessThanZero(givenNumber));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.be.lessThan", i18n("var.name.number"), 0), message);
  }

  @Test
  @DisplayName("checking the number should be less than zero if number is less than zero")
  void shouldBeLessThanZero_IfNumberIsGreaterThanZero_ShouldRunSuccessful() {
    //Given
    var givenNumber = -1;

    //When
    var result = shouldRunSuccessfully(() -> shouldBeLessThanZero(givenNumber));

    //Then
    assertTrue(result);
  }

}
