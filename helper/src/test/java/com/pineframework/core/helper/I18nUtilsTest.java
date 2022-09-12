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

import static com.pineframework.core.helper.I18nUtils.I18N_LANG;
import static com.pineframework.core.helper.I18nUtils.i18n;
import static com.pineframework.core.helper.I18nUtils.i18ns;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link I18nUtilsTest} class provides unit tests for {@link I18nUtils}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see I18nUtils
 * @since 2022-01-01
 */
@DisplayName("I18n Utils Tests")
class I18nUtilsTest extends AbstractUtilsTest {

  @Test
  @DisplayName("getting message")
  void i18n_IfKeyIsValid_ShouldReturnMessage() {
    //Given
    var givenKey = "test.key";

    //When
    var result = i18n(givenKey);

    //Then
    assertNotNull(result);
    assertEquals(givenKey, result);
  }

  @Test
  @DisplayName("getting message")
  void i18ns_IfKeyIsValid_ShouldReturnMessage() {
    //Given
    var givenKey = "test.key.list";
    var givenSplitter = ",";
    var givenParams = new Object[][] {};

    //Expectation
    var expectedMessageNo = 2;

    //When
    var result = i18ns(givenKey, givenSplitter, givenParams);

    //Then
    assertNotNull(result);
    assertEquals(expectedMessageNo, result.length);
    SystemUtils.getEnv(I18N_LANG).ifPresent(locale -> {
      switch (locale) {
        case "en" -> assertThat(result).containsExactly("message1", "message2");
        case "fa" -> assertThat(result).containsExactly("پیام1", "پیام2");
        default -> System.out.printf("unknown locale %s", locale);
      }
    });
  }

  @Test
  @DisplayName("getting message")
  void i18ns_IfKeyIsNotValid_ShouldReturnEmptyArray() {
    //Given
    var givenKey = "test.key.invalidList";
    var givenSplitter = ",";
    var givenParams = new Object[][] {};

    //Expectation
    var expectedMessageNo = 0;

    //When
    var result = i18ns(givenKey, givenSplitter, givenParams);

    //Then
    assertNotNull(result);
    assertEquals(expectedMessageNo, result.length);
  }

  @Test
  @DisplayName("getting message")
  void i18ns_IfKeyIsValidAndNeedParams_ShouldReturnMessage() {
    //Given
    var givenKey = "test.key.list-with-param";
    var givenSplitter = ",";
    var givenParams = new Object[][] {{1}, {"2"}};

    //Expectation
    var expectedMessageNo = 2;

    //When
    var result = i18ns(givenKey, givenSplitter, givenParams);

    //Then
    assertNotNull(result);
    assertEquals(expectedMessageNo, result.length);

    SystemUtils.getEnv(I18N_LANG).ifPresent(locale -> {
      switch (locale) {
        case "en" -> assertThat(result).containsExactly("message1", "message2");
        case "fa" -> assertThat(result).containsExactly("پیام1", "پیام2");
        default -> System.out.printf("unknown locale %s", locale);
      }
    });
  }

  @Test
  @DisplayName("getting message")
  void i18ns_IfParametersNoIsNotEqualsToMessageNo_ShouldThrowIllegalArgumentException() {
    //Given
    var givenKey = "test.key.list-with-param";
    var givenSplitter = ",";
    var givenParams = new Object[][] {{1}, {"2"}, {"dummy"}};

    //Expectation
    var expectedException = IllegalArgumentException.class;
    var expectedExceptionMessage = "parameters rows is not equals to messages number";

    //When
    var result = assertThrows(expectedException, () -> i18ns(givenKey, givenSplitter, givenParams));

    //Then
    assertEquals(expectedExceptionMessage, result.getMessage());
  }

}
