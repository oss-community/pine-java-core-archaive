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
import static com.pineframework.core.helper.validator.ArrayValidator.checkSize;
import static com.pineframework.core.helper.validator.ArrayValidator.isEmptyOrNull;
import static com.pineframework.core.helper.validator.ArrayValidator.isNotEmptyOrNull;
import static com.pineframework.core.helper.validator.ArrayValidator.requireElement;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pineframework.core.helper.AbstractUtilsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link ArrayValidatorTest} class provides unit tests for {@link ArrayValidator}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see ArrayValidator
 * @since 2022-01-01
 */
@SuppressWarnings("ConstantConditions")
@DisplayName("Array Validator Tests")
class ArrayValidatorTest extends AbstractUtilsTest {

  @Test
  @DisplayName("checking array emptiness if the array is null")
  void isEmptyOrNull_IfArrayIsNull_ShouldReturnTrue() {
    //Given
    Object[] givenArray = null;

    //When
    var result = isEmptyOrNull(givenArray);

    // Then
    assertTrue(result);
  }

  @Test
  @DisplayName("checking array emptiness if the array is empty")
  void isEmptyOrNull_IfArrayIsEmpty_ShouldReturnTrue() {
    //Given
    var givenArray = new Object[] {};

    //When
    var result = isEmptyOrNull(givenArray);

    // Then
    assertTrue(result);
  }

  @Test
  @DisplayName("checking array emptiness if the array has atleast one element")
  void isEmptyOrNull_IfArrayHasOneElement_ShouldReturnFalse() {
    //Given
    var givenArray = new Object[] {new Object()};

    //When
    var result = isEmptyOrNull(givenArray);

    // Then
    assertFalse(result);
  }

  @Test
  @DisplayName("checking array fullness if the array is null")
  void isNotEmptyOrNull_IfArrayIsNull_ShouldReturnFalse() {
    //Given
    Object[] givenArray = null;

    //When
    var result = isNotEmptyOrNull(givenArray);

    // Then
    assertFalse(result);
  }

  @Test
  @DisplayName("checking array fullness if the array is empty")
  void isNotEmptyOrNull_IfArrayIsEmpty_ShouldReturnFalse() {
    //Given
    var givenArray = new Object[] {};

    //When
    var result = isNotEmptyOrNull(givenArray);

    // Then
    assertFalse(result);
  }

  @Test
  @DisplayName("checking array fullness if the array has atleast one element")
  void isNotEmptyOrNull_IfArrayHasOneElement_ShouldReturnTrue() {
    //Given
    var givenArray = new Object[] {new Object()};

    //When
    var result = isNotEmptyOrNull(givenArray);

    // Then
    assertTrue(result);
  }

  @Test
  @DisplayName("checking the size of a null array")
  void checkSize_IfArrayIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Object[] givenArray = null;
    var givenSize = 1;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> checkSize(givenArray, givenSize));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.expectation", i18n("var.name.size"), i18n("value.null"), givenSize), message);
  }

  @Test
  @DisplayName("checking the size of an empty array if expectation size is zero")
  void checkSize_IfArrayIsEmptyAndSizeIsZero_ShouldRunSuccessful() {
    //Given
    var givenArray = new Object[] {};
    var givenSize = 0;

    //When
    var result = shouldRunSuccessfully(() -> checkSize(givenArray, givenSize));

    // Then
    assertTrue(result);
  }

  @Test
  @DisplayName("checking the size of an empty array if expectation size is greater than zero")
  void checkSize_IfArrayIsEmptyAndExpectedSizeIsGreaterThanZero_ShouldThrowIllegalArgumentException() {
    //Given
    var givenArray = new Object[] {};
    var givenExpectedSize = 1;

    //Expectation
    var expectedExceptionType = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedExceptionType, () -> checkSize(givenArray, givenExpectedSize));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.expectation", i18n("var.name.size"), givenArray.length, givenExpectedSize), message);
  }

  @Test
  @DisplayName("checking the size of an array include one element if expectation size is one")
  void checkSize_IfArrayHasOneElementAndExpectedSizeIsOne_ShouldRunSuccessful() {
    //Given
    var givenArray = new Object[] {new Object()};
    var givenExpectedSize = 1;

    //When
    var result = shouldRunSuccessfully(() -> checkSize(givenArray, givenExpectedSize));

    // Then
    assertTrue(result);
  }

  @Test
  @DisplayName("checking the size of an array include one element and expectation size is zero")
  void checkSize_IfArrayHasOneElementAndExpectedSizeIsZero_ShouldThrowIllegalArgumentException() {
    //Given
    var givenArray = new Object[] {new Object()};
    var givenExpectedSize = 0;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> checkSize(givenArray, givenExpectedSize));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.expectation", i18n("var.name.size"), givenArray.length, givenExpectedSize), message);
  }

  @Test
  @DisplayName("checking a null array which must have element")
  void requireElement_IfArrayIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Object[] givenArray = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> requireElement(givenArray));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.array")), message);
  }

  @Test
  @DisplayName("checking an empty array which must have element")
  void requireElement_IfArrayIsEmpty_ShouldThrowIllegalArgumentException() {
    //Given
    var givenArray = new Object[] {};

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> requireElement(givenArray));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.array")), message);
  }

  @Test
  @DisplayName("checking an array include one element which must have element")
  void requireElement_IfArrayHasElement_ShouldRunSuccessful() {
    //Given
    var givenArray = new Object[] {new Object()};

    //When
    var result = shouldRunSuccessfully(() -> requireElement(givenArray));

    // Then
    assertTrue(result);
  }

}
