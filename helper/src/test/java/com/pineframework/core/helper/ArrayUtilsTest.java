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

import static com.pineframework.core.helper.ArrayUtils.concat;
import static com.pineframework.core.helper.ArrayUtils.createArray;
import static com.pineframework.core.helper.ArrayUtils.find;
import static com.pineframework.core.helper.ArrayUtils.intersection;
import static com.pineframework.core.helper.ArrayUtils.ofNullable;
import static com.pineframework.core.helper.ArrayUtils.random;
import static com.pineframework.core.helper.ArrayUtils.subtract;
import static com.pineframework.core.helper.ArrayUtils.union;
import static com.pineframework.core.helper.I18nUtils.i18n;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link ArrayUtilsTest} class provides unit tests for {@link ArrayUtils}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see ArrayUtils
 * @since 2022-01-01
 */
@SuppressWarnings("ALL")
@DisplayName("Array Utils Tests")
class ArrayUtilsTest extends AbstractUtilsTest {

  @Test
  @DisplayName("creating array if type is null")
  void createArray_IfTypeIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Class<Object> givenArrayType = null;
    var givenSize = 0;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> createArray(givenArrayType, givenSize));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.type")), message);
  }

  @Test
  @DisplayName("creating array if size of array is less than zero")
  void createArray_IfSizeIsLessThanZero_ShouldThrowIllegalArgumentException() {
    //Given
    var givenSize = -1;
    var givenArrayType = Object.class;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> createArray(givenArrayType, givenSize));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.be.greaterThan", i18n("var.name.size"), 0), message);
  }

  @Test
  @DisplayName("creating an array if type is not null and size is greater than zero")
  void createArray_IfParametersAreValid_ShouldReturnAnEmptyArray() {
    //Given
    var givenArrayType = Object.class;
    var givenSize = 1;

    //Expectation
    var expectedArrayType = Object[].class;
    var expectedSize = 1;

    //When
    var result = createArray(givenArrayType, givenSize);

    //Then
    assertEquals(expectedArrayType, result.getClass());
    assertEquals(expectedSize, result.length);
  }

  @Test
  @DisplayName("creating an empty array if the array is null")
  void ofNullable_IfArrayIsNull_ShouldReturnAnEmptyArray() {
    //Given
    Object[] givenArray = null;

    //Expectation
    var expectedSize = 0;

    //When
    var result = ofNullable(givenArray);

    //Then
    assertNotNull(result);
    assertEquals(Object[].class, result.getClass());
    assertEquals(expectedSize, result.length);
  }

  @Test
  @DisplayName("returning the same array if an array is not null")
  void ofNullable_IfArrayIsNotNull_ShouldReturnTheSameArray() {
    //Given
    var givenArray = new Object[] {new Object()};

    //Expectation
    var expectedSize = 1;

    //When
    var result = ofNullable(givenArray);

    //Then
    assertNotNull(result);
    assertEquals(Object[].class, result.getClass());
    assertEquals(expectedSize, result.length);
    assertThat(result).containsExactly(givenArray);
  }

  @Test
  @DisplayName("choosing a random element of a null array")
  void random_IfArrayIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Object[] givenArray = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> random(givenArray));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.array")), message);
  }

  @Test
  @DisplayName("choosing a random element of an empty array")
  void random_IfArrayIsEmpty_ShouldThrowIllegalArgumentException() {
    //Given
    var givenArray = new Object[] {};

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> random(givenArray));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.emptyOrNull", i18n("var.name.array")), message);
  }

  @Test
  @DisplayName("choosing a random element of an array include one element")
  void random_IfArrayHasOneElement_ShouldReturnTheSameElementForAlways() {
    //Given
    var givenArray = new String[] {"element"};

    //Expectation
    var expectedElement = "element";

    //When
    var result = random(givenArray);

    //Then
    assertNotNull(result);
    assertEquals(expectedElement, result);
  }

  @Test
  @DisplayName("choosing a random element of an array include more than one element")
  void random_IfArrayHasMoreThanOneElement_ShouldReturnAnElement() {
    //Given
    var givenArray = new String[] {"element1", "element2"};

    //When
    var result = random(givenArray);

    //Then
    assertNotNull(result);
    assertThat(result).isIn("element1", "element2");
  }

  @Test
  @DisplayName("finding an element in a null array")
  void find_IfArrayIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Object[] givenArray = null;
    Predicate<Object> givenComparator = o -> true;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> find(givenArray, givenComparator));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.array")), message);
  }

  @Test
  @DisplayName("finding an element in an array with null condition")
  void find_IfComparatorIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    var givenArray = new Object[] {new Object()};
    Predicate<? super Object> givenComparator = null;

    //When
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> find(givenArray, givenComparator));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.comparator")), message);
  }

  @Test
  @DisplayName("finding an element in an empty array")
  void find_IfArrayIsEmpty_ShouldReturnEmptyOptional() {
    //Given
    var givenArray = new Object[] {};
    Predicate<Object> givenComparator = o -> true;

    //When
    var result = find(givenArray, givenComparator);

    //Then
    assertNotNull(result);
    assertFalse(result.isPresent());
  }

  @Test
  @DisplayName("finding an element in an array include more than one element and a true comparator")
  void find_IfParametersAreValid_ShouldReturnAnElement() {
    //Given
    var givenArray = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
    Predicate<Integer> givenComparator = o -> o.equals(7);

    //Expectation
    var expectedElement = 7;

    //When
    var result = find(givenArray, givenComparator);

    //Then
    assertNotNull(result);
    assertTrue(result.isPresent());
    result.ifPresent(value -> assertEquals(expectedElement, value.intValue()));
  }

  @Test
  @DisplayName("finding an element in an array include more than one element and a false comparator")
  void find_IfDoesNotFindAnElementMachWithCondition_ShouldReturnAnEmptyOptional() {
    //Given
    var givenArray = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
    Predicate<Integer> givenComparator = o -> o.equals(0);

    //When
    var result = find(givenArray, givenComparator);

    //Then
    assertNotNull(result);
    assertFalse(result.isPresent());
  }

  @Test
  @DisplayName("subtracting between a null array and a filled array")
  void subtract_IfFirstArrayIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Object[] givenArray1 = null;
    var givenArray2 = new Object[] {new Object()};

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> subtract(givenArray1, givenArray2, Object::equals, Object.class));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.array")), message);
  }

  @Test
  @DisplayName("subtracting between a filled array and a null array")
  void subtract_IfSecondArrayIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    var givenArray1 = new Object[] {new Object()};
    Object[] givenArray2 = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> subtract(givenArray1, givenArray2, Object::equals, Object.class));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.array")), message);
  }

  @Test
  @DisplayName("subtracting between two different arrays")
  void subtract_IfBothArraysAreNotNullAndAreDifferent_ShouldReturnSubsetOfFirstArray() {
    //Given
    var givenArray1 = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
    var givenArray2 = new Integer[] {6, 7, 8, 9};

    //Expectation
    var expectedSize = 5;
    var expectedArray = new Integer[] {1, 2, 3, 4, 5};

    //When
    var result = subtract(givenArray1, givenArray2, Integer::equals, Integer.class);

    //Then
    assertNotNull(result);
    assertEquals(Integer[].class, result.getClass());
    assertEquals(expectedSize, result.length);
    assertThat(result).containsOnly(expectedArray);
  }

  @Test
  @DisplayName("subtracting between a filled array and an empty array")
  void subtract_IfFirstArrayHasElementAndSecondArrayIsEmpty_ShouldReturnFirstArray() {
    //Given
    var givenArray1 = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
    var givenArray2 = new Integer[] {};

    //Expectation
    var expectedSize = 9;
    var expectedArray = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};

    //When
    var result = subtract(givenArray1, givenArray2, Integer::equals, Integer.class);

    //Then
    assertNotNull(result);
    assertEquals(Integer[].class, result.getClass());
    assertEquals(expectedSize, result.length);
    assertThat(result).containsOnly(expectedArray);
  }

  @Test
  @DisplayName("subtracting between an empty array and a filled array")
  void subtract_IfFirstArrayIsEmptyAndSecondArrayHasElement_ShouldReturnAnEmptyArray() {
    //Given
    var givenArray1 = new Object[] {};
    var givenArray2 = new Object[] {new Object()};

    //Expectation
    var expectedSize = 0;

    //When
    var result = subtract(givenArray1, givenArray2, Object::equals, Object.class);

    //Then
    assertNotNull(result);
    assertEquals(Object[].class, result.getClass());
    assertEquals(expectedSize, result.length);
  }

  @Test
  @DisplayName("subtracting between two same arrays")
  void subtract_IfBothArraysAreTheSame_ShouldReturnEmptyArray() {
    //Given
    var givenArray1 = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
    var givenArray2 = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};

    //Expectation
    var expectedSize = 0;

    //When
    var result = subtract(givenArray1, givenArray2, Integer::equals, Integer.class);

    //Then
    assertNotNull(result);
    assertEquals(Integer[].class, result.getClass());
    assertEquals(expectedSize, result.length);
  }

  @Test
  @DisplayName("intersection between a null array and a filled array")
  void intersection_IfFirstArrayIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Object[] givenArray1 = null;
    var givenArray2 = new Object[] {new Object()};

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> intersection(givenArray1, givenArray2, Object::equals, Object.class));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.array")), message);
  }

  @Test
  @DisplayName("intersection between a filled array and a null array")
  void intersection_IfSecondArrayIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    var givenArray1 = new Object[] {new Object()};
    Object[] givenArray2 = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> intersection(givenArray1, givenArray2, Object::equals, Object.class));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.array")), message);
  }

  @Test
  @DisplayName("intersection between a filled array and an empty array")
  void intersection_IfFirstArrayHasElementAndSecondArrayIsEmpty_ShouldReturnFirstArray() {
    //Given
    var givenArray1 = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
    var givenArray2 = new Integer[] {};

    //Expectation
    var expectedSize = 0;

    //When
    var result = intersection(givenArray1, givenArray2, Integer::equals, Integer.class);

    //Then
    assertNotNull(result);
    assertEquals(Integer[].class, result.getClass());
    assertEquals(expectedSize, result.length);
  }

  @Test
  @DisplayName("intersection between an empty array and a filled array")
  void intersection_IfFirstArrayIsEmptyAndSecondArrayHasElement_ShouldReturnAnEmptyArray() {
    //Given
    var givenArray1 = new Object[] {};
    var givenArray2 = new Object[] {new Object()};

    //Expectation
    var expectedSize = 0;

    //When
    var result = intersection(givenArray1, givenArray2, Object::equals, Object.class);

    //Then
    assertNotNull(result);
    assertEquals(Object[].class, result.getClass());
    assertEquals(expectedSize, result.length);
  }

  @Test
  @DisplayName("intersection between two different arrays")
  void intersection_IfBothArraysHaveElementAndTheyAreDifferent_ShouldReturnCommonArray() {
    //Given
    var givenArray1 = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
    var givenArray2 = new Integer[] {1, 2, 3, 4, 5};
    BiPredicate<Integer, Integer> givenComparator = Integer::equals;
    var givenType = Integer.class;

    //Expectation
    var expectedSize = 5;
    var expectedArray = new Integer[] {1, 2, 3, 4, 5};

    //When
    var result = intersection(givenArray1, givenArray2, givenComparator, givenType);

    //Then
    assertEquals(expectedSize, result.length);
    assertThat(result).containsOnly(expectedArray);
  }

  @Test
  @DisplayName("union of a null array and a any array")
  void union_IfArrayIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Object[] givenArray = null;
    var givenVararg = new Object[] {new Object()};

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> union(givenArray, givenVararg));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.array")), message);
  }

  @Test
  @DisplayName("union of a any array and a null array")
  void union_IfVarargIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    var givenArray = new Object[] {new Object()};
    Object[] givenVararg = null;

    //Expectation
    var expectedException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectedException, () -> union(givenArray, givenVararg));

    // Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.vararg")), message);
  }

  @Test
  @DisplayName("union of the different arrays")
  void union_IfBothArraysHaveElement_ShouldReturnNewArrayIncludeAllElementsWithoutRepetitiveElements() {
    //Given
    var givenArray = new Integer[] {1, 3, 5, 6, 7, 8, 9};
    var givenVararg = new Integer[] {1, 2, 3, 4, 5};

    //Expectation
    var expectedSize = 9;
    var expectedArray = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};

    //When
    var result = union(givenArray, givenVararg);

    //Then
    assertNotNull(result);
    assertEquals(expectedSize, result.length);
    assertEquals(Integer[].class, result.getClass());
    assertThat(result).containsOnly(expectedArray);
  }

  @Test
  @DisplayName("union of two same arrays")
  void union_IfBothArraysHaveElement_ShouldReturnNewArrayWithTheSameElements() {
    //Given
    var givenArray = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
    var givenVararg = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};

    //Expectation
    var expectedSize = 9;
    var expectedArray = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9};

    //When
    var result = union(givenArray, givenVararg);

    //Then
    assertNotNull(result);
    assertEquals(expectedSize, result.length);
    assertEquals(Integer[].class, result.getClass());
    assertThat(result).containsOnly(expectedArray);
  }

  @Test
  @DisplayName("concatenate of two different arrays")
  void concat_IfArrayAndVarargHaveElement_ShouldReturnAllElement() {
    //Given
    var givenArray = new Integer[] {1, 3, 5, 6, 7, 8, 9};
    var givenVararg = new Integer[] {1, 2, 3, 4, 5};

    //Expectation
    var expectedArray = new Integer[] {1, 3, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5};
    var expectedSize = givenArray.length + givenVararg.length;

    //When
    var result = concat(givenArray, givenVararg);

    //Then
    assertNotNull(result);
    assertEquals(expectedSize, result.length);
    assertThat(result).containsOnly(expectedArray);
  }

  @Test
  @DisplayName("concatenate of two null arrays")
  void concat_IfBothArraysAreNull_ShouldReturnNullArray() {
    //Given
    Object[] givenArray = null;
    Object[] givenVararg = null;

    //When
    var result = concat(givenArray, givenVararg);

    //Then
    assertNull(result);
  }

  @Test
  @DisplayName("concatenate of a null array with a vararg")
  void concat_IfArrayIsNull_ShouldReturnElementsOfVararg() {
    //Given
    Integer[] givenArray = null;
    var givenVararg = new Integer[] {1, 2, 3, 4, 5};

    //Expectation
    var expectedArray = new Integer[] {1, 2, 3, 4, 5};
    var expectedSize = givenVararg.length;

    //When
    var result = concat(givenArray, givenVararg);

    //Then
    assertNotNull(result);
    assertEquals(expectedSize, result.length);
    assertThat(result).containsOnly(expectedArray);
  }

  @Test
  @DisplayName("concatenate of an array with a null vararg")
  void concat_IfVarargIsNull_ShouldReturnElementsOfArray() {
    //Given
    var givenArray = new Integer[] {1, 2, 3, 4, 5, 6};
    Integer[] givenVararg = null;

    //Expectation
    var expectedArray = new Integer[] {1, 2, 3, 4, 5, 6};
    var expectedSize = givenArray.length;

    //When
    var result = concat(givenArray, givenVararg);

    //Then
    assertNotNull(result);
    assertEquals(expectedSize, result.length);
    assertThat(result).containsOnly(expectedArray);
  }
}
