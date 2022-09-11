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

import static com.pineframework.core.helper.CollectionUtils.concat;
import static com.pineframework.core.helper.CollectionUtils.findFrequency;
import static com.pineframework.core.helper.CollectionUtils.findRepetitiveElements;
import static com.pineframework.core.helper.CollectionUtils.intersection;
import static com.pineframework.core.helper.CollectionUtils.ofNullable;
import static com.pineframework.core.helper.CollectionUtils.subtract;
import static com.pineframework.core.helper.CollectionUtils.union;
import static com.pineframework.core.helper.I18nUtils.i18n;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The {@link CollectionUtils} class provides unit tests for {@link CollectionUtils}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see CollectionUtils
 * @since 2022-01-01
 */
@SuppressWarnings("ALL")
@DisplayName("Collection Utils Tests")
class CollectionUtilsTest extends AbstractUtilsTest {

  @Test
  @DisplayName("creating an empty collection if a collection is null")
  void ofNullable_IfCollectionIsNull_ShouldReturnEmptyCollection() {
    //Given
    Collection<Object> givenCollection = null;

    //When
    var result = ofNullable(givenCollection);

    //Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("returning the same array if an array is not null")
  void ofNullable_IfArrayIsNotNull_ShouldReturnTheSameArray() {
    //Given
    var givenCollection = List.of(1);

    //Expectation
    var expectedSize = 1;
    var expectedElements = new Integer[] {1};


    //When
    var result = ofNullable(givenCollection);

    //Then
    assertNotNull(result);
    assertEquals(expectedSize, result.size());
    assertThat(result).containsExactly(expectedElements);
  }

  @Test
  @DisplayName("finding frequency of a null collection")
  void findFrequency_IfCollectionIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Collection<Object> givenCollection = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> findFrequency(givenCollection));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.collection")), message);
  }

  @Test
  @DisplayName("finding frequency of an empty collection")
  void findFrequency_IfCollectionIsEmpty_ShouldReturnAnEmptyMap() {
    //Given
    var givenCollection = List.of();

    //When
    var result = findFrequency(givenCollection);

    //Then
    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("finding frequency of a collection include many elements")
  void findFrequency_IfCollectionHasElement_ShouldReturnRepeatedElements() {
    //Given
    var givenCollection = List.of(1, 2, 2, 2, 3, 4, 5, 6, 7, 7, 7, 7, 8, 9, 9);

    //When
    var result = findFrequency(givenCollection);

    //Then
    assertEquals(9, result.size());
    assertEquals(1, result.get(1));
    assertEquals(3, result.get(2));
    assertEquals(1, result.get(3));
    assertEquals(1, result.get(4));
    assertEquals(1, result.get(5));
    assertEquals(1, result.get(6));
    assertEquals(4, result.get(7));
    assertEquals(1, result.get(8));
    assertEquals(2, result.get(9));
  }

  @Test
  @DisplayName("finding repetitive elements in a null collection")
  void findRepetitiveElements_IfCollectionIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Collection<Object> givenCollection = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> findRepetitiveElements(givenCollection));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.collection")), message);
  }

  @Test
  @DisplayName("finding repetitive elements in an empty collection")
  void findRepetitiveElements_IfCollectionIsEmpty_ShouldReturnAnEmptyCollection() {
    //Given
    var givenCollection = List.of();

    //When
    var result = findRepetitiveElements(givenCollection);

    //Then
    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("finding repetitive elements in a collection include many elements")
  void findRepetitiveElements_IfCollectionHasElement_ShouldReturnRepeatedElementsAsCollection() {
    //Given
    var givenCollection = List.of(1, 2, 2, 2, 3, 4, 5, 6, 7, 7, 7, 7, 8, 9, 9);

    //Expectation
    var expectedSize = 3;
    var expectedElements = new Integer[] {2, 7, 9};

    //When
    var result = findRepetitiveElements(givenCollection);

    //Then
    assertNotNull(result);
    assertEquals(expectedSize, result.size());
    assertThat(result).containsOnly(expectedElements);
  }

  @Test
  @DisplayName("subtracting between a null collection and another collection")
  void subtract_IfFirstCollectionIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Collection<Object> givenFirstCollection = null;
    var givenSecondCollection = List.of();

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> subtract(givenFirstCollection, givenSecondCollection));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.collection")), message);
  }

  @Test
  @DisplayName("subtracting between a not null collection and a null collection")
  void subtract_IfSecondCollectionIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    var givenFirstCollection = List.of();
    Collection<Object> givenSecondCollection = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> subtract(givenFirstCollection, givenSecondCollection));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.collection")), message);
  }

  @Test
  @DisplayName("subtracting between two different collections")
  void subtract_IfParametersAreValid_ShouldReturnSubSetOfFirstCollection() {
    //Given
    var givenFirstCollection = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
    var givenSecondCollection = List.of(6, 7, 8, 9);

    //Expectation
    var expectedSize = 5;
    var expectedCollection = new Integer[] {1, 2, 3, 4, 5};

    //When
    var result = subtract(givenFirstCollection, givenSecondCollection);

    //Then
    assertNotNull(result);
    assertEquals(expectedSize, result.size());
    assertThat(result).containsOnly(expectedCollection);
  }

  @Test
  @DisplayName("subtracting between a filled collection and an empty collection")
  void subtract_IfFirstCollectionHasElementAndSecondCollectionIsEmpty_ShouldReturnFirstCollection() {
    //Given
    var givenFirstCollection = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
    var givenSecond = List.of();

    //Expectation
    var expectedSize = 9;
    var expectedCollection = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};

    //When
    var result = subtract(givenFirstCollection, givenSecond);

    //Then
    assertNotNull(result);
    assertEquals(expectedSize, result.size());
    assertThat(result).containsOnly(expectedCollection);
  }

  @Test
  @DisplayName("subtracting between an empty collection and another not null collection")
  void subtract_IfFirstCollectionIsEmptyAndSecondCollectionIsNotNull_ShouldReturnAnEmptyCollection() {
    //Given
    var givenFirstCollection = List.of();
    var givenSecondCollection = List.of(new Object());

    //When
    var result = subtract(givenFirstCollection, givenSecondCollection);

    //Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("subtracting between two same collections")
  void subtract_IfBothCollectionsAreTheSame_ShouldReturnAnEmptyCollection() {
    //Given
    var givenFirstCollection = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
    var givenSecondCollection = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

    //When
    var result = subtract(givenFirstCollection, givenSecondCollection);

    //Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("intersection between a null collection and a filled collection")
  void intersection_IfFirstCollectionIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Collection<Object> givenFirstCollection = null;
    var givenSecondCollection = List.of();

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> intersection(givenFirstCollection, givenSecondCollection));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.collection")), message);
  }

  @Test
  @DisplayName("intersection between a filled collection and a null collection")
  void intersection_IfSecondCollectionIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    var givenFirstCollection = List.of();
    Collection<Object> givenSecondCollection = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> intersection(givenFirstCollection, givenSecondCollection));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.collection")), message);
  }

  @Test
  @DisplayName("intersection between a filled collection and an empty collection")
  void intersection_IfFirstCollectionHasElementAndSecondCollectionIsEmpty_ShouldReturnAnEmptyCollection() {
    //Given
    var givenFirstCollection = List.of(new Object());
    var givenSecondCollection = List.of();

    //When
    var result = intersection(givenFirstCollection, givenSecondCollection);

    //Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("intersection between an empty collection and a filled collection")
  void intersection_IfFirstCollectionIsEmptyAndSecondCollectionHasElement_ShouldReturnAnEmptyCollection() {
    //Given
    var givenFirstCollection = List.of();
    var givenSecondCollection = List.of(new Object());

    //When
    var result = intersection(givenFirstCollection, givenSecondCollection);

    //Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("intersection between two different collections")
  void intersection_IfBothCollectionsHaveElementAndTheyAreDifferent_ShouldReturnCommonCollection() {
    //Given
    var givenFirstCollection = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
    var givenSecondCollection = List.of(1, 2, 3, 4, 5);

    //Expectation
    var expectedSize = 5;
    var expectedArray = new Integer[] {1, 2, 3, 4, 5};

    //When
    var result = intersection(givenFirstCollection, givenSecondCollection);

    //Then
    assertEquals(expectedSize, result.size());
    assertThat(result).containsOnly(expectedArray);
  }

  @Test
  @DisplayName("intersection between two same collections")
  void intersection_IfBothCollectionsAreTheSame_ShouldReturnTheSameCollection() {
    //Given
    var givenFirstCollection = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
    var givenSecondCollection = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

    //Expectation
    var expectedSize = 9;
    var expectedCollection = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};

    //When
    var result = intersection(givenFirstCollection, givenSecondCollection);

    //Then
    assertEquals(expectedSize, result.size());
    assertThat(result).containsOnly(expectedCollection);
  }

  @Test
  @DisplayName("union of a null collection and an empty collection")
  void union_IfFirstCollectionIsNullAndSecondCollectionIsEmpty_ShouldThrowIllegalArgumentException() {
    //Given
    Collection<Object> givenFirstCollection = null;
    var givenSecondCollection = List.of();

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> union(givenFirstCollection, givenSecondCollection));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.collection")), message);
  }

  @Test
  @DisplayName("union of an empty collection and a null collection")
  void union_IfFirstCollectionIsEmptyAndSecondCollectionIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    var givenFirstCollection = List.of();
    Collection<Object> givenSecondCollection = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> union(givenFirstCollection, givenSecondCollection));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.collection")), message);
  }

  @Test
  @DisplayName("union of two different collections")
  void union_IfBothCollectionsHaveElement_ShouldReturnCollectionIncludeAllElementsWithoutRepetitiveElements() {
    //Given
    var givenFirstCollection = List.of(1, 3, 5, 6, 7, 8, 9);
    var givenSecondCollection = List.of(1, 2, 3, 4, 5);

    //Expectation
    var expectedSize = 9;
    var expectedCollection = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};

    //When
    var result = union(givenFirstCollection, givenSecondCollection);

    //Then
    assertEquals(expectedSize, result.size());
    assertThat(result).containsOnly(expectedCollection);
  }

  @Test
  @DisplayName("union of two same collections")
  void union_IfBothCollectionsHaveElement_ShouldReturnNewCollectionWithTheSameElements() {
    //Given
    var firstCollection = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
    var secondCollection = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

    //Expectation
    var expectedSize = 9;
    var expectedCollection = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};

    //When
    var result = union(firstCollection, secondCollection);

    //Then
    assertEquals(expectedSize, result.size());
    assertThat(result).containsOnly(expectedCollection);
  }

  @Test
  @DisplayName("concatenate of two different collections")
  void concat_WithTwoDifferentCollection_ShouldReturnAllElement() {
    //Given
    var givenFirstCollection = List.of(1, 3, 5, 6, 7, 8, 9);
    var givenSecondCollection = List.of(1, 2, 3, 4, 5);

    //Expectation
    var expectedElements = new Integer[] {1, 3, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5};
    var expectedSize = givenFirstCollection.size() + givenSecondCollection.size();

    //When
    var result = concat(givenFirstCollection, givenSecondCollection);

    //Then
    assertNotNull(result);
    assertEquals(expectedSize, result.size());
    assertThat(result).containsOnly(expectedElements);
  }

  @Test
  @DisplayName("concatenate of two null collections")
  void concat_IfBothCollectionsAreNull_ShouldReturnNullArray() {
    //Given
    Collection<Object> givenFirstCollection = null;
    Collection<Object> givenSecondCollection = null;

    //When
    var result = concat(givenFirstCollection, givenSecondCollection);

    //Then
    assertNull(result);
  }

}
