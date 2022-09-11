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
import static com.pineframework.core.helper.NumberUtils.getSign;
import static com.pineframework.core.helper.NumberUtils.getSignChar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pineframework.core.helper.validator.NumberValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * {@link NumberUtilsTest} class provides unit tests for {@link NumberUtils}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see NumberUtils
 * @since 2022-01-01
 */
@DisplayName("Number Utils Tests")
class NumberUtilsTest extends AbstractUtilsTest {

  @Test
  @DisplayName("getting sign as a character if number is greater than zero")
  void getSignChar_IfNumberIsGreaterThanZero_ShouldReturnPlusSign() {
    //Given
    var givenNumber = 1;

    //Expectation
    var expectationSign = "+";

    //When
    var result = getSignChar(givenNumber);

    //Then
    assertEquals(expectationSign, result);
  }

  @Test
  @DisplayName("getting sign as a character if number is less than zero")
  void getSignChar_IfNumberIsLessThanZero_ShouldReturnMinusSign() {
    //Given
    var givenNumber = -1;

    //Expectation
    var expectationSign = "-";

    //When
    var result = getSignChar(givenNumber);

    //Then
    assertEquals(expectationSign, result);
  }

  @Test
  @DisplayName("getting sign as a character if number is equals to zero")
  void getSignChar_IfNumberIsEqualsToZero_ShouldReturnEmptyString() {
    //Given
    var givenNumber = 0;

    //Expectation
    var expectationSign = "";

    //When
    var result = getSignChar(givenNumber);

    //Then
    assertEquals(expectationSign, result);
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 1, 2})
  @DisplayName("getting sign if number is greater than or equals to zero")
  void getSign_Integer_IfNumberIsGreaterThanOrEqualsToZero_ShouldReturnOne(int givenNumber) {
    //Expectation
    var expectationSign = 1;

    //When
    var result = getSign(givenNumber);

    //Then
    assertEquals(expectationSign, result);
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, -2, -3})
  @DisplayName("getting sign if number is less than zero")
  void getSign_Integer_IfNumberIsLessThanZero_ShouldReturnMinusOne(int givenNumber) {
    //Expectation
    var expectationSign = -1;

    //When
    var result = getSign(givenNumber);

    //Then
    assertEquals(expectationSign, result);
  }


  @ParameterizedTest
  @ValueSource(strings = {"0", "1", "2"})
  @DisplayName("getting sign if string number is greater than or equals to zero")
  void getSign_String_IfNumberIsGreaterThanOrEqualsToZero_ShouldReturnOne(int givenNumber) {
    //Expectation
    var expectationSign = 1;

    //When
    var result = getSign(givenNumber);

    //Then
    assertEquals(expectationSign, result);
  }

  @ParameterizedTest
  @ValueSource(strings = {"-1", "-2", "-3"})
  @DisplayName("getting sign if string number is less than zero")
  void getSign_String_IfNumberIsLessThanZero_ShouldReturnMinusOne(String givenNumber) {
    //Expectation
    var expectationSign = -1;

    //When
    var result = getSign(givenNumber);

    //Then
    assertEquals(expectationSign, result);
  }

  @Test
  @DisplayName("checking the number is less than zero if number is less than zero")
  void shouldNotBeLessThanZero_IfNumberIsLessThanZero_ShouldThrowIllegalArgumentException() {
    //Given
    var givenNumber = -1;

    //Expectation
    var expectationException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectationException, () -> NumberValidator.shouldBeGreaterThanZero(givenNumber));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.be.greaterThan", i18n("var.name.number"), 0), message);
  }

  @Test
  @DisplayName("checking the number is less than zero if number is greater than zero")
  void shouldNotBeLessThanZero_IfNumberIsGreaterThanZero_ShouldRunSuccessful() {
    //Given
    var givenNumber = 1;

    //When
    var result = shouldRunSuccessfully(() -> NumberValidator.shouldBeGreaterThanZero(givenNumber));

    //Then
    assertTrue(result);
  }

}
