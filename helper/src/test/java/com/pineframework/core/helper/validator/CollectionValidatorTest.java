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
import static com.pineframework.core.helper.validator.CollectionValidator.checkSize;
import static com.pineframework.core.helper.validator.CollectionValidator.isEmptyOrNull;
import static com.pineframework.core.helper.validator.CollectionValidator.isNotEmpty;
import static com.pineframework.core.helper.validator.CollectionValidator.requireElement;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pineframework.core.helper.AbstractUtilsTest;
import io.vavr.control.Try;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The {@link CollectionValidatorTest} class provides unit tests for {@link CollectionValidator}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see CollectionValidator
 * @since 2022-01-01
 */
@SuppressWarnings("ConstantConditions")
@DisplayName("Collection Validator Tests")
class CollectionValidatorTest extends AbstractUtilsTest {

  @Test
  @DisplayName("checking collection emptiness if the collection is null")
  void isEmptyOrNull_IfCollectionIsNull_ShouldReturnTrue() {
    //Given
    Collection<Object> givenCollection = null;

    //When
    var result = isEmptyOrNull(givenCollection);

    // Then
    assertTrue(result);
  }

  @Test
  @DisplayName("checking collection emptiness if the collection is empty")
  void isEmptyOrNull_IfCollectionIsEmpty_ShouldReturnTrue() {
    //Given
    Collection<Object> givenCollection = List.of();

    //When
    var result = isEmptyOrNull(givenCollection);

    // Then
    assertTrue(result);
  }

  @Test
  @DisplayName("checking collection emptiness if the collection has atleast one element")
  void isEmptyOrNull_IfCollectionHasElement_ShouldReturnFalse() {
    //Given
    List<Object> givenCollection = List.of(new Object());

    //When
    var result = isEmptyOrNull(givenCollection);

    // Then
    assertFalse(result);
  }

  @Test
  @DisplayName("checking collection fullness if the collection is null")
  void isNotEmpty_IfCollectionIsNull_ShouldReturnFalse() {
    //Given
    Collection<Object> givenCollection = null;

    //When
    var result = isNotEmpty(givenCollection);

    // Then
    assertFalse(result);
  }

  @Test
  @DisplayName("checking collection fullness if the collection is empty")
  void isNotEmpty_IfCollectionIsEmpty_ShouldReturnFalse() {
    //Given
    Collection<Object> givenCollection = List.of();

    //When
    var result = isNotEmpty(givenCollection);

    // Then
    assertFalse(result);
  }

  @Test
  @DisplayName("checking collection fullness if the collection has atleast one element")
  void isNotEmpty_IfCollectionHasElement_ShouldReturnTrue() {
    //Given
    List<Object> givenCollection = List.of(new Object());

    //When
    var result = isNotEmpty(givenCollection);

    // Then
    assertTrue(result);
  }

  @Test
  @DisplayName("checking the size of a null collection")
  void checkSize_IfCollectionIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Collection<Object> givenCollection = null;
    var givenSize = 0;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> checkSize(givenCollection, givenSize));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.expectation", i18n("var.name.size"), "null", givenSize), message);
  }

  @Test
  @DisplayName("checking the size of an empty collection if expectation size is zero")
  void checkSize_IfCollectionIsEmptyAndSizeIsZero_ShouldRunSuccessful() {
    //Given
    Collection<Object> givenCollection = List.of();
    var givenSize = 0;

    //When
    var result = Try.run(() -> checkSize(givenCollection, givenSize)).isSuccess();

    // Then
    assertTrue(result);
  }

  @Test
  @DisplayName("checking the size of an empty collection if expectation size greater than zero")
  void checkSize_IfCollectionIsEmptyAndSizeIsGreaterThanZero_ShouldThrowIllegalArgumentException() {
    //Given
    Collection<Object> givenCollection = List.of();
    var givenSize = 1;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> checkSize(givenCollection, givenSize));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.expectation", i18n("var.name.size"), givenCollection.size(), givenSize), message);
  }

  @Test
  @DisplayName("checking the size of a collection include one element if expectation size is one")
  void checkSize_IfCollectionHasElementAndExpectedSizeIsOne_ShouldRunSuccessful() {
    //Given
    Collection<Object> givenCollection = List.of(new Object());
    var givenSize = 1;

    //When
    var result = shouldRunSuccessfully(() -> checkSize(givenCollection, givenSize));

    // Then
    assertTrue(result);
  }

  @Test
  @DisplayName("checking the size of a collection include one element if expectation size is zero")
  void checkSize_IfCollectionHasElementAndSizeIsZero_ShouldThrowIllegalArgumentException() {
    //Given
    Collection<Object> givenCollection = List.of(new Object());
    var givenSize = 0;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> checkSize(givenCollection, givenSize));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.expectation", i18n("var.name.size"), givenCollection.size(), givenSize), message);
  }

  @Test
  @DisplayName("checking a null collection which must have element")
  void requireElement_IfCollectionIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Collection<Object> givenCollection = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> requireElement(givenCollection));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.empty", i18n("var.name.collection")), message);
  }

  @Test
  @DisplayName("checking an empty collection which must have element")
  void requireElement_IfCollectionIsEmpty_ShouldThrowIllegalArgumentException() {
    //Given
    List<Object> givenCollection = List.of();

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> requireElement(givenCollection));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.empty", i18n("var.name.collection")), message);
  }

  @Test
  @DisplayName("checking a collection include one element which must have element")
  void requireElement_IfCollectionHasElement_ShouldRunSuccessful() {
    //Given
    List<Object> givenCollection = List.of(new Object());

    //When
    var result = shouldRunSuccessfully(() -> requireElement(givenCollection));

    // Then
    assertTrue(result);
  }
}
