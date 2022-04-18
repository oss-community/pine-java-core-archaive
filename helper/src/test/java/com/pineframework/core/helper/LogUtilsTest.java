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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link LogUtilsTest} class provides unit tests for {@link LogUtils}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see LogUtils
 * @since 2022-01-01
 */
@DisplayName("Log Utils Tests")
@SuppressWarnings("ConstantConditions")
class LogUtilsTest extends AbstractUtilsTest {

  @Test
  @DisplayName("getting logger with the name")
  void getLogger_IfNameIsNotEmptyOrNull_ShouldReturnLogger() {
    var result = LogUtils.getLogger("Test");

    assertNotNull(result);
    assertTrue(result instanceof org.slf4j.Logger);
    result.info("test logger is successful");
  }
}