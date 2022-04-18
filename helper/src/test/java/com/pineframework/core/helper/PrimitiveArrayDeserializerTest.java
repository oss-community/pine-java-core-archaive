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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import io.vavr.control.Try;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

/**
 * {@link PrimitiveArrayDeserializerTest} class provides unit tests for {@link PrimitiveArrayDeserializer}.
 *
 * @author Saman Alishirishahrbabak
 * @version 1.0.0
 * @see PrimitiveArrayDeserializer
 * @since 2022-01-01
 */

@DisplayName("PrimitiveArrayDeserializer Tests")
class PrimitiveArrayDeserializerTest {

  private PrimitiveArrayDeserializer deserializer;

  @BeforeEach
  void setUp() {
    deserializer = new PrimitiveArrayDeserializer();
  }

  @ParameterizedTest
  @ArgumentsSource(ArraysArgumentsProvider.class)
  @DisplayName("deserialize arrays")
  void deserialize_IfParametersAreValid_ShouldReturnPrimitiveType(String input, Object[] expectedResult) {

    Object[] result = Try.of(() -> {
      JsonFactory factory = new MappingJsonFactory();
      JsonParser parser = factory.createParser(input);
      return deserializer.deserialize(parser, null);
    }).get();

    assertArrayEquals(expectedResult, result);
  }

  /**
   * The {@link ArraysArgumentsProvider} provides JSON array as a <i>given values</i> and type-base data as a <i>expectation result</i>.
   *
   * @author Saman Alishirishahrbabak
   * @version 1.0.0
   * @since 2022-01-01
   */
  static class ArraysArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return Stream.of(
          Arguments.of(
              "[1,2,3]",
              new Integer[] {1, 2, 3}
          ),
          Arguments.of(
              "[\"1990-01-01\",\"2000-01-01\",\"2020-01-01\"]",
              new LocalDate[] {LocalDate.of(1990, 1, 1), LocalDate.of(2000, 1, 1), LocalDate.of(2020, 1, 1)}
          ),
          Arguments.of(
              "[\"true\",\"false\"]",
              new Boolean[] {true, false}
          ),
          Arguments.of(
              "[\"string1\",\"string2\",\"string3\"]",
              new String[] {"string1", "string2", "string3"})
      );
    }
  }
}
