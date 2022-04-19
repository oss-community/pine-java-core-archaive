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

import static com.pineframework.core.helper.EnumUtils.findEnumByFilter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;
import java.util.function.Predicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link EnumUtilsTest} class provides unit tests for {@link EnumUtils}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see EnumUtils
 * @since 2022-01-01
 */
@DisplayName("Enum Utils Tests")
class EnumUtilsTest extends AbstractUtilsTest {

  @Test
  @DisplayName("getting an enum instance if filter is acceptable")
  void findEnumByFilter_IfFilterIsAcceptable_ShouldReturnOptionalOfEnumInstance() {
    //Given
    var givenEnumType = TestEnum.class;
    Predicate<TestEnum> givenPredicate = instance -> Objects.equals(instance.key, 1);

    //Expected
    var expectedEnum = TestEnum.TEST_1;

    //When
    var result = findEnumByFilter(givenEnumType, givenPredicate);

    //Then
    assertNotNull(result);
    assertTrue(result.isPresent());
    result.ifPresent(e -> {
      assertEquals(expectedEnum.key, e.key);
      assertEquals(expectedEnum.value, e.value);
    });
  }

  @Test
  @DisplayName("getting an enum instance if filter is unacceptable")
  void findEnumByPredicate_IfFilterIsNotAcceptable_ShouldReturnAnEmptyOptional() {
    //Given
    var givenEnumType = TestEnum.class;
    Predicate<TestEnum> givenPredicate = instance -> Objects.equals(instance.key, -1);

    //When
    var result = findEnumByFilter(givenEnumType, givenPredicate);

    //Then
    assertNotNull(result);
    assertFalse(result.isPresent());
  }

  enum TestEnum {
    TEST_1(1, "test_1"),
    TEST_2(2, "test_2"),
    TEST_3(3, "test_3"),
    DEFAULT(0, "default"),
    ;

    private final Integer key;
    private final String value;

    TestEnum(Integer key, String value) {
      this.key = key;
      this.value = value;
    }
  }
}
