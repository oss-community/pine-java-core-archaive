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

import static com.pineframework.core.helper.I18nUtils.I18N_LOCALE;
import static com.pineframework.core.helper.I18nUtils.i18nByBundle;
import static com.pineframework.core.helper.I18nUtils.i18nsByBundle;
import static com.pineframework.core.helper.TestEnvironmentConfig.EN_US_LOCALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
  @DisplayName("get message")
  void i18n_IfKeyIsValid_ShouldReturnMessage() {
    var result = i18nByBundle("test_i18n", "test.key");

    assertNotNull(result);

    SystemUtils.getEnv(I18N_LOCALE).ifPresent(locale -> {
      switch (locale) {
        case "en_US" -> assertEquals("message", result);
        case "fa_IR" -> assertEquals("پیام", result);
        default -> System.out.printf("unknown locale %s", locale);
      }
    });
  }

  @Test
  @DisplayName("get message")
  void i18ns_IfKeyIsValid_ShouldReturnMessage() {
    var result = i18nsByBundle("test_i18n", "test.key.list", ",", new Object[][] {});

    assertNotNull(result);
    assertEquals(2, result.length);

    SystemUtils.getEnv(I18N_LOCALE).ifPresent(locale -> {
      switch (locale) {
        case EN_US_LOCALE -> assertThat(result).containsExactly("message1", "message2");
        case "fa_IR" -> assertThat(result).containsExactly("پیام1", "پیام2");
        default -> System.out.printf("unknown locale %s", locale);
      }
    });
  }

  @Test
  @DisplayName("get message")
  void i18ns_IfKeyIsValidAndNeedParams_ShouldReturnMessage() {
    var result = i18nsByBundle("test_i18n", "test.key.list-with-param", ",", new Object[][] {{1}, {"2"}});

    assertNotNull(result);
    assertEquals(2, result.length);

    SystemUtils.getEnv(I18N_LOCALE).ifPresent(locale -> {
      switch (locale) {
        case EN_US_LOCALE -> assertThat(result).containsExactly("message1", "message2");
        case "fa_IR" -> assertThat(result).containsExactly("پیام1", "پیام2");
        default -> System.out.printf("unknown locale %s", locale);
      }
    });
  }
}
