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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {@link YesNoQuestionTest} class provides unit tests for {@link YesNoQuestion}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see YesNoQuestion
 * @since 2022-01-01
 */
@DisplayName("Yes_No Question Tests")
class YesNoQuestionTest extends AbstractUtilsTest {

  @Test
  void no_IfExceptionHappens_ShouldThrowTheException() {
    //Expectation
    var expectedException = RuntimeException.class;

    //When
    var result = assertThrows(expectedException, () -> YesNoQuestion.ask(false)
        .no(() -> {
          throw new RuntimeException("exception");
        })
    );

    //Then
    assertNotNull(result);
    assertEquals("exception", result.getMessage());
  }

  @Test
  void no_IfTaskRunSuccessful_ShouldRunNo() {
    //Given
    var givenValue = false;
    var holder = new ObjectHolder<>(0);

    //When
    YesNoQuestion.ask(givenValue)
        .yes(() -> holder.object = 1)
        .no(() -> holder.object = -1);

    //Then
    assertEquals(-1, holder.object);
  }

  @Test
  void yes_IfExceptionHappens_ShouldThrowTheException() {
    //Expectation
    var expectedException = RuntimeException.class;

    //When
    var result = assertThrows(expectedException, () -> YesNoQuestion.ask(true)
        .yes(() -> {
          throw new RuntimeException("exception");
        })
    );

    //Then
    assertNotNull(result);
    assertEquals("exception", result.getMessage());
  }

  @Test
  void yes_IfTaskRunSuccessful_ShouldRunYes() {
    //Given
    var givenValue = true;
    var holder = new ObjectHolder<>(0);

    //When
    YesNoQuestion.ask(givenValue)
        .no(() -> holder.object = -1)
        .yes(() -> holder.object = 1);

    //Then
    assertEquals(1, holder.object);
  }

  static class ObjectHolder<T> {
    private T object;

    ObjectHolder(T object) {
      this.object = object;
    }
  }
}
