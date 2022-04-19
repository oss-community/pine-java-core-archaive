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

import static com.pineframework.core.helper.SystemUtils.getEnv;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link SystemUtilsTest} class provides unit tests for {@link SystemUtils}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see SystemUtils
 * @since 2022-01-01
 */
@DisplayName("System Utils Tests")
class SystemUtilsTest extends AbstractUtilsTest {

  @BeforeEach
  void setUp() {
    System.setProperty("test.property", "dummy");
  }

  @Test
  @DisplayName("getting environment variables if it was added by application")
  void getEnv_IfEnvironmentVariableWasAdded_ShouldReturnString() {
    //Given
    var givenKey = "test.property";

    //When
    var result = getEnv(givenKey);

    //Then
    assertNotNull(result);
    assertTrue(result.isPresent());
    result.ifPresent(value -> assertEquals("dummy", value));
  }

  @Test
  @DisplayName("getting environment variables if it is not exist")
  void getEnv_IfEnvironmentVariableIsNotExist_ShouldReturnString() {
    //Given
    var givenKey = "invalid.property";

    //When
    var result = getEnv(givenKey);

    //Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }
}