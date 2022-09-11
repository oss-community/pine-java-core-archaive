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
import static com.pineframework.core.helper.validator.ObjectValidator.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pineframework.core.helper.AbstractUtilsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link ObjectValidatorTest} class provides unit tests for {@link ObjectValidator}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see ObjectValidator
 * @since 2022-01-01
 */
@SuppressWarnings("ConstantConditions")
@DisplayName("Object Utils Tests")
class ObjectValidatorTest extends AbstractUtilsTest {

  @Test
  @DisplayName("checking object is null if the object is null")
  void requireNonNull_IfObjectIsNull_ShouldThrowIllegalArgumentException() {
    //Given
    Object givenObject = null;

    //Expectation
    var expectationException = IllegalArgumentException.class;

    //When
    var result = assertThrows(expectationException, () -> requireNonNull(givenObject));

    //Then
    assertNotNull(result);
    var message = result.getMessage();
    logErrorAsInfo(message);
    assertEquals(i18n("error.validation.should.not.be.null", i18n("var.name.data")), message);
  }

  @Test
  @DisplayName("checking object is null if the object is not null")
  void requireNonNull_IfObjectIsNotNull__ShouldRunSuccessful() {
    //Given
    Object givenObject = new Object();

    //When
    var result = shouldRunSuccessfully(() -> requireNonNull(givenObject));

    //Then
    assertTrue(result);
  }
}
